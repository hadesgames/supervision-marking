package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.example.SessionFactoryManager;
import org.example.models.FullAnswer;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;
import java.util.Map;


@Path("/")
public class AnswerResource {

    private Session session;

    public AnswerResource() {
        session = SessionFactoryManager.getInstance().openSession();
    }

    @GET
    @Path("answers")
    @ViewWith("/soy/answers.index")
    public Map<String, ?> getAnswers() {
        List<FullAnswer> answers = session.createCriteria(FullAnswer.class)
                                       .list();
        session.close();
        return ImmutableMap.of("answers", answers);
    }
    /*
    Uploading files has a separate controller
    @POST
    @Path("answers")
    public void addAnswer() {
        session.close();
        throw new RedirectException("/answers/upload");
    }

    */

    @GET
    @Produces("application/pdf")
    @Path("answers/{id}")
    public Response getAnswer(@PathParam("id") int id) {
        FullAnswer answer = (FullAnswer) session.get(FullAnswer.class, id);

        if (answer == null)
            throw new NotFoundException();

        File file = new File(answer.getFilepath());

        if (!file.exists())
            throw new NotFoundException();

        Response.ResponseBuilder response = Response.ok(file);
        // TODO get rid of yummy
        response.header("Content-Disposition", "filename=\"yummy.pdf\"");

        session.close();
        return response.build();
    }

    @PUT
    @Path("answers/{id}")
    public void updateAnswer() {
        session.close();
    }

    @DELETE
    @Path("answers/{id}")
    public void deleteAnswer() {
        session.close();

    }

}
