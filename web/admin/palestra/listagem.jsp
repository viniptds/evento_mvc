<%@page import="model.Evento"%>
<%@page import="model.Palestra"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Palestra> lpal;
    Palestra p;    
    String search = "";
    int cod;
    Evento ev;
%>

<%
    ev = null;
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
        
    if(session.getAttribute("altered_evento") != null)
    {
        ev = (Evento)session.getAttribute("altered_evento");
    }
    else
    {
        response.sendRedirect("EventoController");
    }
    
    
    if(session.getAttribute("listaPal") != null)
    {
        lpal = (ArrayList<Palestra>)session.getAttribute("listaPal");
    }    
    
%>
            
        <a href="<%out.print(application.getContextPath());%>/EventoController<%out.print((ev!=null) ? "?path=perfil.jsp&codevt="+ev.getCodigo() : "");%>">Menu</a>                        
        <form method="post" action="<%out.print(application.getContextPath());%>/PalestraController?path=listagem.jsp&list=true">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/PalestraController?path=perfil.jsp">Nova Palestra</a>
            
        
<%
    if(lpal != null)
    {        
        if(lpal.size()>0)
        {
%>        
        <br>
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Descrição</td>
                <td>Capacidade Total</td>
                <td>Vagas Disponíveis</td>                
            </tr>
            
<% 
            for(Palestra p : lpal)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/PalestraController?path=perfil.jsp&codpal=<%
                        out.print(p.getCod()); %>"> 
                        <% out.print(p.getNome()); %>
                    </a>
                </td>

                <td><% out.print(p.getDescricao()); %></td>                  
                <td><% out.print(p.getCapacidade()); %></td>  
                <td><% out.print((p.getAlunos() == null) ? p.getCapacidade() : p.getCapacidade()-p.getAlunos().size()); %></td>  
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
        <p> Não há palestra(s) para essa busca. </p>
<%      }
    }
%> 