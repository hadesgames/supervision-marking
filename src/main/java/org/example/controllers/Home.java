package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.ViewWith;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Map;

@Path("/")
public class Home{

    @Path("")
    @GET
    @ViewWith("/soy/home.home")
    public Map<String, ?> showSailingBoat() {
        return ImmutableMap.of();
    }

}
