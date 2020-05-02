
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    Usuario us;
%>

<%
    //session.invalidate();
    if(session.getAttribute("user") == null)
        response.sendRedirect("controller/index");
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
        <p>Operações:</p>
        <ul>
            <li>
                <a href="./perfil.jsp">Novo Usuário</a>
            </li>
            
            <li>
                <a href="./listagem.jsp">Listar Usuários</a>
            </li>                        
            <br>
            <li>
                <a href="aluno">Gerenciar Alunos</a>
            </li>
        </ul>                
        
        
        <p> <a href="index?action=logout">Sair</a></p>
    </body>
</html>
