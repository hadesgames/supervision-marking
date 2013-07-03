package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.forms.FileUploadForm;
import org.example.pdfTools.PdfManip;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.*;
import java.nio.file.*;
import java.util.Collection;
import java.util.Iterator;
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
    public void handleFormUpload(@MultipartForm FileUploadForm file) throws IOException {

        System.out.println("Shut");
        System.out.println(file.file.length);
        OutputStream op = new FileOutputStream("mata.pdf");
        op.write(file.file);
        op.close();

        throw new RedirectException("/");
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

        File file = new File("/home/ap760/cur/supervision-marking/temp/" + fileName + ".pdf");

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
        File root = new File("/home/ap760/cur/supervision-marking/temp/Store");
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
}
