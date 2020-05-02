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
    int cod;
    String nome, login, senha;
%>


<%
    nome = login = "";
    
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("index.jsp");
    }
    else
    {
        if(request.getParameter("cod") != null)
        {
            try 
            {
                cod = Integer.parseInt(request.getParameter("cod"));
                
                if(request.getParameter("delete") != null)
                {
                    if(((Usuario)session.getAttribute("user")).getCodigo() == cod)
                        response.sendRedirect("perfil.jsp");    
                    else
                        usd.remove(u);
                }

                if(request.getParameter("update") != null)
                {

                }
            

                u = usd.get(cod);
                
                if(u == null)
                {
                    response.sendRedirect("listagem.jsp");
                }
                else
                {
                    nome = u.getNome();
                    login = u.getLogin();
                }
                
                
                
            }
            catch(NumberFormatException ex)
            {
                System.out.println("Impossível converter");
                response.sendRedirect("perfil.jsp");
            }
        }
        if(request.getParameter("bNew") != null)
        {
            if(request.getParameter("nome") != null)
            {
                nome = request.getParameter("nome");
                
                if(request.getParameter("login") != null)
                {
                    login = request.getParameter("logni");
                    
                    if(request.getParameter("senha") != null)
                    {
                        senha = request.getParameter("senha");
                        
                        if(nome.length() > 0 && login.length() > 0 && senha.length() > 0)
                        {
                            u = new Usuario(0, nome, login, senha);
                            usd.insert(u);
                            
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
        <a href="perfil.jsp">Menu</a>
        <form action="cadastro" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            <label>Login: </label>
            <input type="text" name="login" required="required" value="<% out.print(login); %>">
            <br>
            <label>Senha: </label>
            <input type="text" name="senha" required="required">
            <br>
            <input type="submit" name="bNew" value="Enviar">
        </form>
<%
    if(u != null)
    {            
%>


        <a href="perfil.jsp?cod=<%out.print(u.getCodigo());%>&delete=true">Deletar</a>

<%
    }
%>
    </body>
</html>
