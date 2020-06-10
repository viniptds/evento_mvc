<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.Evento"%>

<%!
    Evento evt;    
    int cod = 0;
    String nome = "";
    LocalDate inicio = null, fim = null;
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
            <input type="text" name="inicio" required="required" value="<% out.print((inicio == null) ? "" : inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); %>">
            <br>
            
            <label>Fim: </label>
            <input type="text" name="fim" required="required" value="<% out.print((fim == null) ? "" : fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); %>">
            <br>
                        
            <input type="submit" name="bChange" value="Enviar">
        </form>
<%
    if(session.getAttribute("altered_evento") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/EventoController?path=listagem.jsp&codevt=<%
               out.print(evt.getCodigo());%>&delete=true&list=true">Deletar</a>
               <br>
               <br>
               
               <a href="PalestraController?path=listagem.jsp&list=true&evt=<%out.print(evt.getCodigo());%>">Visualizar palestras</a>
        <br>
        
        <a href="">Relatório de Presença</a>
        
               
<%
    }
%>
