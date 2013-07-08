package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.SessionFactoryManager;
import org.example.forms.FileUploadForm;
import org.example.helpers.PathHelper;
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

    private void saveFileToDisk(File file, byte[] data) throws IOException {
        OutputStream op = new FileOutputStream(file);
        op.write(data);
        op.close();
    }

    private String getMime(String filename) throws IOException {
        return Files.probeContentType(Paths.get(filename));
    }

    @Path("")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void handleFormUpload(@MultipartForm FileUploadForm file) {
        try {
            String filename = PathHelper.getFilename(file.filename);
            String ext = PathHelper.getFileExtension(file.filename);

            File tempFile = PathHelper.getTempFile(filename, ext);
            File pdfFile = PathHelper.getPdfFile(filename);

            saveFileToDisk(tempFile, file.file);
            convertToPDF(tempFile, pdfFile);
            tempFile.delete();


            Session session = SessionFactoryManager.getInstance().openSession();
            session.beginTransaction();

            FullAnswer answer = new FullAnswer(pdfFile.getPath());

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

    private void convertToPDF(File input, File output) throws IOException, COSVisitorException {
        String filename = input.getPath();
        String mime = getMime(filename);

        if (mime.equals("application/pdf")) {
            input.renameTo(output);
        } else if (mime.equals("text/plain")) {
            String contents = FileUtils.readFileToString(input);

            PDDocument document = PdfConverter.textToPdf(contents);
            document.save(output);
            document.close();
        } else if (mime.contains("image")) {
            List<String> image_list = new LinkedList<String>();
            image_list.add(input.getPath());

            PDDocument document = PdfConverter.imagesToPdf(image_list);
            document.save(output);
            document.close();
        } else {

          System.out.println("WTF? " + mime);
         // TODO Should throw an error
        }
    }
}
