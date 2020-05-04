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
            nome = login = senha = "";
            cod = 0;
            response.sendRedirect("index.jsp");            
        }
        
        if(session.getAttribute("altered_user") != null)
        {
            u = (Usuario)session.getAttribute("altered_user");
            nome = u.getNome();
            login = u.getLogin();
            cod = u.getCodigo();             
            
            if(request.getParameter("delete") != null)
            {
                if(request.getParameter("delete").equals("true"))
                {
                    if(!u.getLogin().equals("admin"))
                    {
                        usd.remove(u);
                        System.out.println("Removido!");                    
                    }

                    if(u.getLogin().equals(((Usuario)session.getAttribute("user")).getLogin()))
                        session.removeAttribute("user");    
                }
                response.sendRedirect("index");
            }
        }
               
        if(request.getParameter("bChange") != null)
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
                                session.removeAttribute("altered_user");
                                System.out.println("Alterado!");
                            }
                            else
                            {
                                usd.insert(u);                                
                            }
                            cod = 0;
                            nome = login = senha = "";
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

        <a href="perfil.jsp?cod=<%out.print(u.getCodigo());%>&delete=true">Deletar</a>

<%
    }
%>
    </body>
</html>
