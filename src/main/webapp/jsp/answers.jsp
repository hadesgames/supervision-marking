<jsp:useBean id="model" class="java.util.Map" scope="request"/>
<%
    java.util.ArrayList<org.example.models.FullAnswer> answers = (java.util.ArrayList<org.example.models.FullAnswer>) model.get("answers");
%>

    <html>
        <head>
            <!-- Javascript -->
            <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.0.min.js"> </script>
            <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min.js"></script>
            <script type="text/javascript" src="/static/javascript/main.js"> </script>

            <!-- CSS -->
            <link rel="stylesheet" type="text/css" href="/static/bootstrap/css/bootstrap.css"/>
            <link rel="stylesheet" type="text/css" href="/static/css/flat-ui.css"/>
        </head>
        <body>
            <div class="navbar navbar-inverse">
                <div class="navbar-inner">
                    <div class="container">
                        <ul class="nav">
                            <li> <a href="#"> Supervison Marking </a> </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="container">
                            <table class="table">
                                <thead>
                                    <th> Id </th>
                                    <th> Link </th>
                                </thead>
                             <% for(org.example.models.FullAnswer answer: answers) { %>
                                <tr>
                                    <td> <%= answer.getId() %> </td>
                                    <td> <a href="/answers/<%= answer.getId() %>"> bla.pdf </a> </td>
                                    <td> <a class="delete btn btn-danger" href="/answers/<%= answer.getId() %>"> <i class="fui-cross"></i></a></td>
                                </tr>
                            <% } %>

                            </table>

                            <form class="file-upload" method="post" action="/answers/upload" enctype="multipart/form-data">
                                <input type="file" name="file"/>
                                <input type="submit" class="btn btn-primary" name="submit" value="Upload"/>
                            </form>
            </div>
        </body>
    </html>
