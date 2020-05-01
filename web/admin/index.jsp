
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    Usuario us;
%>

<%
    if(session.getAttribute("user") == null)
        response.sendRedirect("/");
    else
    {
        us = (Usuario)session.getAttribute("user");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Home</title>
    </head>
    <body>
        <h1>Controle Administrativo</h1>
        
        <p>Logado como: <i><% out.print(us.getLogin()); %> </i> </p>
        
        <p> <a href="logout">Sair</a></p>
    </body>
</html>
