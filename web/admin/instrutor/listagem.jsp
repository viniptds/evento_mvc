
<%@page import="model.Palestra"%>
<%@page import="model.Instrutor"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Instrutor> lins;
    Instrutor in;    
    Palestra pal;
    String search = "";
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
    
    if(session.getAttribute("altered_pal") != null)
    {
        pal = (Palestra)session.getAttribute("altered_pal");
    }
    else
    {
        pal = null;
        response.sendRedirect("PalestraController");
    }
    
    if(session.getAttribute("listaInst") != null)
    {
        lins = (ArrayList<Instrutor>)session.getAttribute("listaInst");
    }    
    request.removeAttribute("bChange");    
%>
            
<%
    if(pal != null)
    {
%>
        <a href="<%out.print(application.getContextPath()+"/PalestraController?path=perfil.jsp&codpal="+pal.getCod());%>">Voltar</a>      
<%
    }
%>                       
        <form method="post" action="<%out.print(application.getContextPath()+"/InstrutorController?"+request.getQueryString());%>">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/InstrutorController?path=perfil.jsp">Novo Instrutor</a>
            
        
<%
    if(lins != null)
    {        
        if(lins.size()>0)
        {
%>        
        <br>
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Currículo</td>                
            </tr>
            
<% 
            for(Instrutor u : lins)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/InstrutorController?path=perfil.jsp&codinst=<%
                        out.print(u.getCodigo()); %>"> 
                        <% out.print(u.getNome()); %>
                    </a>
                </td>

                <td><% out.print(u.getCurriculo()); %></td>                
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
        <p> Não há instrutor(es) para essa busca. </p>
<%      }
    }
%> 