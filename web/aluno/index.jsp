<%@page import="model.Aluno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    Aluno al = null;    
%>

<%        
    if(session.getAttribute("aluno") == null)
    {
        response.sendRedirect("index");
    }
    else
    {
        al = (Aluno)session.getAttribute("aluno");
    }    
    

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
       
<%
    if(al != null)
    {
%>

        <p>Olá, <%out.println(al.getNome() != null ? al.getNome() : "");%>
        <p> <a href="ApplicationController?action=logout">Sair</a></p>
        <p>
            Funções do Aluno:
        </p>
        <ul>
            <li>Manutenção</li>
        </ul>
<%
}
%>
    </body>
</html>
