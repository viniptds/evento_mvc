
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%!
    Usuario us;
    UsuarioDAO usd = new UsuarioDAO();
%>

<%
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
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
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <h1>Gerenciamento dos Alunos</h1>
        <p>Logado como: <%out.print(us.getLogin());%></p>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/AdmAlunoController?path=perfil.jsp">Cadastrar Aluno</a>
            </li>
            <li>
                <a href="">Listar Aluno(s)</a>
            </li>
        </ul>
    </body>
</html>
