<%-- 
    Document   : perfil.jsp
    Created on : 01/05/2020, 08:19:39
    Author     : viniciuspadovan
--%>
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%!
    Usuario u;
    UsuarioDAO usd = new UsuarioDAO();
    int cod = 0;
    String nome = "", login = "", senha = "";
%>


<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("AdminController");
    }
    
        
        if(session.getAttribute("altered_user") != null)
        {
            u = (Usuario)session.getAttribute("altered_user");
            nome = u.getNome();
            login = u.getLogin();
            cod = u.getCodigo();             
        }                   
        else
        {
            nome = login = senha = "";
        }

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil</title>
    </head>
    <body>        
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <form action="<%out.print(application.getContextPath());%>/AdminController?path=usuario/listagem.jsp&list=true" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            
            <label>Login: </label>
            <input type="text" name="login" <% if(session.getAttribute("altered_user") != null) out.print("readonly='readonly'");%> required="required" value="<% out.print(login); %>">
            <br>
            
            <label>Senha: </label>
            <input type="password" name="senha" required="required">
            <br>
            
            <input type="submit" name="bChange" value="Enviar">
        </form>
<%
    if(session.getAttribute("altered_user") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController?path=usuario/listagem.jsp&cod=<%out.print(u.getCodigo());%>&delete=true&list=true">Deletar</a>

<%
    }
%>
    </body>
</html>