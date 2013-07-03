package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.forms.FileUploadForm;
import org.example.pdfconverter.PdfConverter;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("/")
public class Home{

    @Path("")
    @GET
    @ViewWith("/soy/home.home")
    public Map<String, Object> showSailingBoat() {
        return ImmutableMap.of();
    }


    @Path("upload")
    @GET
    @ViewWith("/soy/home.upload")
    public Map<String, Object> showUploadForm() {
       System.out.println("plm");
       return ImmutableMap.of();

    }


    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void handleFormUpload(@MultipartForm FileUploadForm file) throws IOException, COSVisitorException {
        String filename = FilenameUtils.getName(file.filename);

        String finalFilename = "bla_" + filename;

        OutputStream op = new FileOutputStream(finalFilename);
        op.write(file.file);
        op.close();

        String mime = Files.probeContentType(Paths.get(finalFilename));

        handleUploadedFile(filename, mime);

        throw new RedirectException("/upload");
    }

    private void handleUploadedFile(String filename, String mime) throws IOException, COSVisitorException {
        if (mime.equals("application/pdf")) {
            File file = new File(filename);
            File newDest = new File("res.pdf");

            file.renameTo(newDest);
        } else if (mime.equals("text/plain")) {
            File file = new File(filename);
            InputStream stream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);
            String contents = new String(buffer);

            PDDocument document = PdfConverter.textToPdf(contents);
            document.save("res.pdf");
            document.close();
        } else if (mime.contains("image")) {
            List<String> image_list = new LinkedList<String>();
            image_list.add(filename);
            PDDocument document = PdfConverter.imagesToPdf(image_list);
            document.save("res.pdf");
            document.close();
        }
    }


}
