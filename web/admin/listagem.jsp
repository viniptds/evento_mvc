
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Usuario> luser;
    Usuario u;
    UsuarioDAO usd = new UsuarioDAO();
    String search;
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("index.jsp");
    
    if(request.getParameter("cod") != null)
    {
        try 
        {
            cod = Integer.parseInt(request.getParameter("cod"));
            
            if(cod > 0)
            {
                u = usd.get(cod);
                session.setAttribute("altered_user", u);
                response.sendRedirect("perfil.jsp");
            }
            else
                response.sendRedirect("listagem.jsp");
        }
        catch(NumberFormatException ex)
        {
            response.sendRedirect("listagem.jsp");
        }
        
    }    
    
    search = request.getParameter("search");
    
    if(request.getParameter("bSearch") == null)
    {
        luser = usd.list();
    }
    else
    {
        luser = usd.search(search, "usu_login");
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
        <form method="GET" action="listagem.jsp">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search != null && search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">            
        </form>
            <a href="perfil.jsp">Novo Usuário</a>
            
        
<%
    if(luser != null)
    {        
        if(luser.size()>0)
        {
%>        
        <br>
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
                <td><a href="listagem.jsp?cod=<%out.print(u.getCodigo()); %>"> <% out.print(u.getNome()); %></a></td>

                <td><% out.print(u.getLogin()); %></td>                
            </tr>
            
<%
            }
%>
        </table>
<%
        }   
        else
        {
%>
        <p> Não há usuário(s) para essa busca. </p>
<%      }
    }
%>
    </body>
</html>
