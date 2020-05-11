
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Usuario> luser;
    Usuario u;
    UsuarioDAO usd = new UsuarioDAO();
    String search = "";
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("AdminController");
    
    if(session.getAttribute("listaUser") != null)
    {
        luser = (ArrayList<Usuario>)session.getAttribute("listaUser");                
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
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        <p>Listagem de Usuários</p>
        <form method="GET" action="<%out.print(application.getContextPath());%>/AdminController?list=true&path=listagem.jsp">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search != null && search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">            
        </form>
            <a href="<%out.print(application.getContextPath());%>/AdminController?path=usuario/perfil.jsp">Novo Usuário</a>
            
        
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
                <td>
                    <a href="<%out.print(application.getContextPath());%>/AdminController
                    ?path=usuario/perfil.jsp&coduser=<%out.print(u.getCodigo()); %>"> 
                        <% out.print(u.getNome()); %>
                    </a>
                </td>

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
