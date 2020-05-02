
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%!
    Usuario us;
    UsuarioDAO usd = new UsuarioDAO();
%>

<%
    if(session.getAttribute("user") == null)
        response.sendRedirect("index");
    else
    {
        us = (Usuario)session.getAttribute("user");
        
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Controle de Alunos</title>
    </head>
    <body>
        <a href="index">Menu</a>
        <h1>Gerenciamento dos Alunos</h1>
        <p>Logado como: <%out.print(us.getLogin());%></p>
        
        <ul>
            <li>
                <a href="new.jsp">Cadastrar Aluno</a>
            </li>
        </ul>
    </body>
</html>
