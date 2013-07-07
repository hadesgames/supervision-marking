package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.SessionFactoryManager;
import org.example.forms.FileUploadForm;
import org.example.models.FullAnswer;
import org.example.pdfTools.PdfConverter;
import org.hibernate.Session;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("/answers/upload")
public class UploadController {
    // TODO hard coded paths
    private String getFileName(String initialFilename) {
        String filename = FilenameUtils.getName(initialFilename);

       return "temp/bla_" + filename;
    }

    private void saveFileToDisk(String filename, byte[] data) throws IOException {
        OutputStream op = new FileOutputStream(filename);
        op.write(data);
        op.close();
    }

    private String getMime(String filename) throws IOException {
        return Files.probeContentType(Paths.get(filename));
    }

    private String getPDFLocation(String filename) {
        return "temp/res_" + filename + ".pdf";
    }

    private static void cleanUp(String filename) {
        (new File(filename)).delete();
    }

    @Path("")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void handleFormUpload(@MultipartForm FileUploadForm file) {
        try {
            String filename = getFileName(file.filename);
            String pdfFilename = getPDFLocation(filename);

            saveFileToDisk(filename, file.file);
            convertToPDF(filename, pdfFilename);
            System.out.println(filename);
            System.out.println(pdfFilename);
            cleanUp(filename);

            Session session = SessionFactoryManager.getInstance().openSession();
            session.beginTransaction();

            FullAnswer answer = new FullAnswer(pdfFilename);

            session.save(answer);
            session.getTransaction().commit();

            answer.generateFragments();

            session.close();

        } catch (IOException e) {
           e.printStackTrace();
           return ;
        } catch (COSVisitorException e) {
            e.printStackTrace();
            return ;
        }

        throw new RedirectException("/answers");
    }

    private void convertToPDF(String filename, String output) throws IOException, COSVisitorException {
        String mime = getMime(filename);
        if (mime.equals("application/pdf")) {
            File file = new File(filename);
            File newDest = new File(output);

            file.renameTo(newDest);
        } else if (mime.equals("text/plain")) {
            File file = new File(filename);
            InputStream stream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);
            String contents = new String(buffer);
            System.out.println(contents.substring(1,20));

            PDDocument document = PdfConverter.textToPdf(contents);
            document.save(output);
            document.close();
        } else if (mime.contains("image")) {
            List<String> image_list = new LinkedList<String>();
            image_list.add(filename);
            PDDocument document = PdfConverter.imagesToPdf(image_list);
            document.save(output);
            document.close();
        } else {

          System.out.println("WTF? " + mime);
         // TODO Should throw an error
        }
    }
}
