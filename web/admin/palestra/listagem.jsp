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
        ev = null;
        response.sendRedirect("EventoController");
    }
        
    if(session.getAttribute("listaPal") != null)
    {
        lpal = (ArrayList<Palestra>)session.getAttribute("listaPal");
    }        
%>

<%
    if(ev != null)
    {
%>      

        <a href="<%out.print(application.getContextPath()+"/EventoController?hd=2&path=perfil.jsp&codevt="+ev.getCodigo());%>">Voltar</a>                        

<%
    }
%>
  
        <form method="post" action="<%out.print(application.getContextPath()+"/PalestraController?"+request.getQueryString());%>">            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            
        <a href="<%out.print(application.getContextPath());%>/PalestraController?hd=2&path=perfil.jsp">Nova Palestra</a>
            
        
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
                <td>Descri��o</td>
                <td>Capacidade Total</td>
                <td>Vagas Dispon�veis</td>                
            </tr>
            
<% 
            for(Palestra p : lpal)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/PalestraController?hd=2&path=perfil.jsp&codpal=<%
                        out.print(p.getCod()); %>"> 
                        <% out.print(p.getNome()); %>
                    </a>
                </td>

                <td><% out.print(p.getDescricao()); %></td>                  
                <td><% out.print(p.getCapacidade()); %></td>  
                <td><% out.print((p.getAlunos()== null) ? p.getCapacidade() : p.getCapacidade()-p.getAlunos().size()); %></td>  
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
        <p> N�o h� palestra(s) para essa busca. </p>
<%      }
    }
%> 