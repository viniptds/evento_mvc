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
        response.sendRedirect("index");
    }
    else
    {
        if(request.getParameter("path") != null)
        {
            session.removeAttribute("altered_user");
            response.sendRedirect("index.jsp");
        }
        
        if(session.getAttribute("altered_user") != null)
        {
            u = (Usuario)session.getAttribute("altered_user");
            nome = u.getNome();
            login = u.getLogin();
            cod = u.getCodigo();             
        }
                
        if(request.getParameter("bNew") != null)
        {
            if(request.getParameter("nome") != null)
            {
                nome = request.getParameter("nome");
                
                if(request.getParameter("login") != null)
                {
                    login = request.getParameter("login");
                    
                    if(request.getParameter("senha") != null)
                    {
                        senha = request.getParameter("senha");
                        
                        if(nome.length() > 0 && login.length() > 0 && senha.length() > 0)
                        {
                            u = new Usuario(cod, nome, login, senha);
                            if(session.getAttribute("altered_user") != null)
                            {                                
                                usd.update(u);
                            }
                            else
                            {
                                usd.insert(u);
                                System.out.println("Inserido");
                            }
                            response.sendRedirect("listagem.jsp");                            
                        }
                    }
                }
            }
        }
        
        
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
        <a href="perfil.jsp?path=index">Menu</a>
        <form action="perfil.jsp" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            
            <label>Login: </label>
            <input type="text" name="login" required="required" value="<% out.print(login); %>">
            <br>
            
            <label>Senha: </label>
            <input type="password" name="senha" required="required">
            <br>
            
            <input type="submit" name="bNew" value="Enviar">
        </form>
<%
    if(cod > 0)
    {            
%>


        <a href="perfil.jsp?cod=<%out.print(u.getCodigo());%>&delete=true">Deletar</a>

<%
    }
%>
    </body>
</html>
