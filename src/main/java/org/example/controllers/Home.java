package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.example.forms.FileUploadForm;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Path("/")
public class Home{

    @Path("")
    @GET
    @ViewWith("/soy/home.home")
    public Map<String, Object> showSailingBoat() {
        System.out.println("Muie4");
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

        System.out.println("SADSADASDASDASDADAS");
        System.out.println(file.file.length);
        OutputStream op = new FileOutputStream("mata.pdf");
        op.write(file.file);
        op.close();

       throw new RedirectException("/");
    }


}
