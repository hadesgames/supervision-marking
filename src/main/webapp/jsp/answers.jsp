<jsp:useBean id="model" class="java.util.Map" scope="request"/>
<%@ page import = "java.util.*,org.example.models.*"%>
<%
    List<FullAnswer> answers = (List<FullAnswer>) model.get("answers");
%>

<%@include file="/jsp/layout_start.jsp" %>
    <table class="table">
        <thead>
            <th> Id </th>
            <th> Link </th>
        </thead>

     <% for(org.example.models.FullAnswer answer: answers) { %>
        <tr>
            <td> <%= answer.getId() %> </td>
            <td> <a href="/answers/<%= answer.getId() %>"> <%= answer.getName() %> </a> </td>
            <td> <a class="delete btn btn-danger" href="/answers/<%= answer.getId() %>"> <i class="fui-cross"></i></a></td>
        </tr>
    <% } %>

    </table>


    <form class="file-upload" method="post" action="/answers/upload" enctype="multipart/form-data">
        <fieldset>
            <legend> Upload a new answer </legend>
            <input type="file" name="file"/>
            <input type="submit" class="btn btn-primary" name="submit" value="Upload"/>
        </fieldset>
    </form>


    <form class="getFragments" action="/fragments/" method="post">
        <fieldset>
            <legend> Get a page  </legend>
            <input type="text" name="page"/>
            <input type="submit" class="btn btn-info"/>
        </fieldset>
    </form>

<%@include file="/jsp/layout_end.jsp" %>
