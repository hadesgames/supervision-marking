package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.ViewWith;
import org.example.SessionFactoryManager;
import org.example.models.FullAnswer;
import org.hibernate.Session;

import javax.ws.rs.*;
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

    @POST
    @Path("answers")
    public void addAnswer() {

        session.close();
    }

    @GET
    @Path("answers/{id}")
    public void getAnswer() {

        session.close();
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
