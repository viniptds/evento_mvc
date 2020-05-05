<%-- 
    Document   : index
    Created on : 04/05/2020, 22:33:46
    Author     : viniciuspadovan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("user") != null || session.getAttribute("aluno") != null)
    {        
        response.sendRedirect("ApplicationController");        
    }    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MVC Eventos</title>
    </head>
    <body>        
        <h1>Controlador de Eventos com MVC</h1>
        <p>Bem-vindo(a), faça login abaixo para acessar as opções:</p>
        <br>
        
        <form action="ApplicationController?action=login" method="post">

            <label>Acesso: </label>
            <select name="option">
                <option value="0">Aluno</option>
                <option value="1">Usuário</option>
            </select>
            <br>
            
            <label>Login: </label>
            <input type="text" name="login" required="required" placeholder="Usuário e/ou Email">
            <br>

            <label>Password: </label>
            <input type="password" name="password" required="required">
            <br>

            <input type="submit" name="bSubmit">        
        </form>
    </body>
</html>
