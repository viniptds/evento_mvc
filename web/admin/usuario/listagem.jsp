
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Usuario> luser;
    Usuario u;    
    String search = "";
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
    
    if(session.getAttribute("listaUser") != null)
    {
        luser = (ArrayList<Usuario>)session.getAttribute("listaUser");
    }    
    
%>
            
        <a href="<%out.print(application.getContextPath());%>/UsuarioController">Menu</a>                
        
        <form method="post" action="<%out.print(application.getContextPath());%>/UsuarioController?path=listagem.jsp&list=true">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=perfil.jsp">Novo Usuário</a>
            
        
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
                    <a href="<%out.print(application.getContextPath());
                       %>/UsuarioController?path=perfil.jsp&coduser=<%
                        out.print(u.getCodigo()); %>"> 
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