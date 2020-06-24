<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="model.Evento"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Evento> levt;
    Evento evt;    
    String search = "";
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
    
    if(session.getAttribute("listaEvento") != null)
    {
        levt = (ArrayList<Evento>)session.getAttribute("listaEvento");
    }    
    
%>
            
        <a href="<%out.print(application.getContextPath());%>/EventoController">Menu</a>                
        
        <form method="post" action="<%out.print(application.getContextPath());%>/EventoController?hd=1&path=listagem.jsp&list=true">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/EventoController?hd=2&path=perfil.jsp">Novo Evento</a>
            
        
<%
    if(levt != null)
    {        
        if(levt.size()>0)
        {
%>        
        <br>
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Inicio</td>  
                <td>Fim</td> 
            </tr>
            
<% 
            for(Evento u : levt)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/EventoController?hd=2&path=perfil.jsp&codevt=<%
                        out.print(u.getCodigo()); %>"> 
                        <% out.print(u.getNome()); %>
                    </a>
                </td>

                <td><% out.print(u.getInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); %></td>

                <td><% out.print(u.getFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); %></td>                
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
        <p> Não há eventos para essa busca. </p>
<%      }
    }
%> 
