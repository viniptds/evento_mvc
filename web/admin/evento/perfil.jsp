<%@page import="java.time.LocalDate"%>
<%@page import="model.Evento"%>
<%@page import="dao.EventoDAO"%>

<%!
    Evento evt;
    EventoDAO insd = new EventoDAO();
    int cod = 0;
    String nome = "";
    LocalDate inicio, fim;
%>

<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("ApplicationController");
    }    
        
    if(session.getAttribute("altered_evento") != null)
    {
        evt = (Evento)session.getAttribute("altered_evento");
        nome = evt.getNome();
        inicio = evt.getInicio();
        fim = evt.getFim();
        cod = evt.getCodigo();        
    }                   
    else
    {
        nome = "";
        inicio=null;
        fim=null;
    }

%>

        <a href="<%out.print(application.getContextPath());%>/EventoController">Menu</a>
        
        <form action="<%out.print(application.getContextPath());%>/EventoController?path=listagem.jsp&list=true" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            
            <label>Inicio: </label>
            <input type="text" name="inicio" required="required" value="<% out.print(inicio); %>">
            <br>
            
            <label>Fim: </label>
            <input type="text" name="fim" required="required" value="<% out.print(fim); %>">
            <br>
                        
            <input type="submit" name="bChange" value="Enviar">
        </form>
<%
    if(session.getAttribute("altered_evento") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/EventoController?path=listagem.jsp&cod=<%
               out.print(evt.getCodigo());%>&delete=true&list=true">Deletar</a>

<%
    }
%>
