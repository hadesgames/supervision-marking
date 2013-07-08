package org.example.controllers;

import com.google.common.collect.ImmutableMap;
import com.googlecode.htmleasy.RedirectException;
import com.googlecode.htmleasy.ViewWith;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.example.SessionFactoryManager;
import org.example.helpers.PathHelper;
import org.example.models.FragmentAnswer;
import org.example.models.FullAnswer;
import org.example.pdfTools.PdfManip;
import org.h2.expression.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
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
        List<FullAnswer> answers = session.createCriteria(FullAnswer.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                                       .list();

        List hash_answers = new LinkedList();
        for (FullAnswer answer: answers)
            hash_answers.add(ImmutableMap.of("id", answer.getId(), "name", answer.getName()));

        session.close();
        return ImmutableMap.of("answers", hash_answers);
    }

    private Response pdfResponse(String path, String name) {
        return pdfResponse(new File(path), name);
    }
    private Response pdfResponse(File pdf, String name) {

        if (!pdf.exists())
            throw new NotFoundException();

        Response.ResponseBuilder response = Response.ok(pdf);

        response.header("Content-Disposition", "filename=\"" + name + "\"");
        return response.build();

    }

    @GET
    @Produces("application/pdf")
    @Path("answers/{id}")
    public Response getAnswer(@PathParam("id") int id) {
        FullAnswer answer = (FullAnswer) session.get(FullAnswer.class, id);

        if (answer == null)
            throw new NotFoundException();


        session.close();
        return pdfResponse(answer.getFilepath(), answer.getName() + ".pdf");
    }

    @PUT
    @Path("answers/{id}")
    public void updateAnswer() {
        session.close();
    }

    @DELETE
    @Path("answers/{id}")
    public void deleteAnswer(@PathParam("id") int id) {
        FullAnswer answer = (FullAnswer) session.get(FullAnswer.class, id);

        answer.delete(session);


        session.beginTransaction();
        session.delete(answer);
        session.getTransaction().commit();

        session.close();
    }

    @Path("fragments")
    @POST
    @Produces("application/pdf")
    public Response getPage(@FormParam("page") int page) {
       List<FragmentAnswer> pages = session.createCriteria(FragmentAnswer.class)
                                           .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                                           .add(Restrictions.eq("page", page))
                                           .list();

       List<String> docs = new LinkedList<String>();
       for (FragmentAnswer fragment: pages) {
           docs.add(fragment.getFilepath());
       }

        try {
            File tempFile = PathHelper.getTempFile("page_query", ".pdf");
            PDDocument document = PdfManip.merge(docs);
            document.save(tempFile);
            document.close();
            Response resp =  pdfResponse(tempFile, "pages.pdf");
            return resp;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        throw new NotFoundException();

    }

}
