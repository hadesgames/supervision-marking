package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.forms.FileUploadForm;
import org.apache.commons.io.FileUtils;
import org.example.pdfTools.PdfConverter;
import org.example.pdfTools.PdfManip;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/")
public class Home{

    @Path("")
    @GET
    @ViewWith("/soy/home.home")
    public Map<String, Object> showSailingBoat() {
        System.out.println("Up4It");
        return ImmutableMap.of();
    }





    @Path("download/{many:.*}")
    @GET
    @Produces("application/pdf")
    public Response getFileInPDFFormat(@PathParam("many") String fileName)
    {
        System.out.println("File requested is : " + fileName);

        if (fileName == null || fileName.isEmpty())
        {
            ResponseBuilder response = Response.status(Status.BAD_REQUEST);

            return response.build();
        }

        File file = new File("temp/" + fileName + ".pdf");

        if (!file.exists())
            throw new RedirectException("/");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("1", "2");
        return response.build();
    }

    @Path("getPage/{id}")
    @GET
    @Produces("application/pdf")
    public Response getPages(@PathParam("id") int page) throws IOException {

        PDDocument rez = new PDDocument();
        File root = new File("temp/Store");
        String[] extensions = {"pdf"};

        Collection files = FileUtils.listFiles(root, extensions, true);

        for (Object file1 : files) {
            File file = (File) file1;

            try {
                rez.addPage(PdfManip.getPage(PDDocument.load(file.getAbsolutePath()), page));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            rez.save("temp/sol/Merged.pdf");
            rez.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        throw new RedirectException("download/sol/Merged");
    }

    @Path("download-test")
    @GET
    @ViewWith("/soy/home.download")
    public Map<String, ?> bla(){
        return ImmutableMap.of();
    }
}
