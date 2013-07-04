package org.example;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import com.googlecode.htmleasy.HtmleasyProviders;

public class App extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> myServices = new HashSet<Class<?>>();

        // Add my own JAX-RS annotated classes
        myServices.add(org.example.controllers.Home.class);
        myServices.add(org.example.controllers.UploadController.class);
        myServices.add(org.example.controllers.AnswerResource.class);

        // Add Htmleasy Providers
        myServices.addAll(HtmleasyProviders.getClasses());

        return myServices;
    }
}
