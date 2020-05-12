
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu - Usuário</title>
    </head>
    <body>
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        <h1>Menu Usuário</h1>
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=perfil.jsp">Novo Usuário</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=listagem.jsp&list=true">Listar Usuário(s)</a>
            </li>  
        </ul>
    </body>
</html>
