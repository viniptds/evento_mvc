
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Usuario> luser;
    UsuarioDAO usd = new UsuarioDAO();
    String search;
%>

<%
    if(session.getAttribute("user") == null)
        response.sendRedirect("index.jsp");
    
    search = request.getParameter("search");
    
    if(request.getParameter("bSearch") == null)
    {
        luser = usd.list();
        System.out.println("Conectou no remoto");
    }
    else
    {
        luser = luser; //usd.search(request.getParameter("search"));
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de Usuários</title>
    </head>
    <body>
        <a href="index.jsp">Menu</a>
        <p>Listagem de Usuários</p>
        <form method="GET" action="cadastro">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search != null && search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">            
        </form>
            <a href="perfil.jsp">Novo Usuário</a>
            
        
<%
    if(luser != null)
    {            
%>        
        
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Login</td>                
            </tr>
<% 
        for(Usuario u : luser)
        {
%>

            <tr>
                <td><a href="perfil.jsp?cod=<%out.print(u.getCodigo()); %>"> <% out.print(u.getNome()); %></a></td>

                <td><% out.print(u.getLogin()); %></td>
                
            </tr>
<%
        }
%>
        </table>
<%
    }
%>
        
    </body>
</html>
