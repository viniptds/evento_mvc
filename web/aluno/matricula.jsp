<%@page import="model.Palestra"%>
<%@page import="model.Matricula"%>
<%@page import="model.Evento"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Evento> evts = null;    
    Evento evt;
    Matricula mat;
    Palestra palestra;
%>
<%
    if(session.getAttribute("aluno") == null)
    {
        response.sendRedirect("ApplicationController");
    }
    
    if(session.getAttribute("altered_mat") != null)
    {
        mat = (Matricula)session.getAttribute("altered_mat");
    }
    else
        mat = null;
    
    if(session.getAttribute("listaEvt") != null)
    {
        evts = (ArrayList<Evento>)session.getAttribute("listaEvt");
    }
    else
        evts = null;
    
    if(session.getAttribute("evento") != null)
    {
        evt = (Evento)session.getAttribute("evento");                               
    }
    else
        evt = null;
    if(session.getAttribute("palestra") != null)
    {
        palestra = (Palestra)session.getAttribute("palestra");
    }
    else
        palestra = null;

        

%>

    <a href="AlunoController">Menu</a>
    <form action="MatriculaController?path=matricula.jsp" method="post">

<%
    if(evts != null && evts.size() > 0)
    {
%>
        <p>Selecione Evento:</p>
        <select name="evento">
            
<%
        for(Evento e : evts)
        { 
%>

            <option value="<%out.print(e.getCodigo());%>" selected="<%
                out.print((mat != null && mat.getEvento() == e) ? "selected": "");%>">
                <%out.print(e.getNome());%>
            </option>
            
<%
        }
%>
        </select>
        <input type="submit" name="bEvt" value="Selecionar">
    </form> 
        
<%
    if(evt != null) 
    {
%>
    <form action="MatriculaController?path=matricula.jsp?evento=<%out.print(evt.getCodigo());%>" method="post">
        <p>Selecione Palestra</p>
        <select name="palestra">
<%
        for (Palestra p : evt.getPals())
        {
%>
            <option value="<%out.print(p.getCod());%>" selected="<%
                out.print((mat != null && mat.getEvento().getPals().contains(p)) ? "true": "false");%>">
                <%out.print(p.getNome());%></option>
<%
        }
%>
        </select>
        <input type="submit" name="bPal" value="Enviar">
    </form>
<%
        if(mat != null)
        {
%>

        <a href="MatriculaController?path=listagem.jsp&list=true&delete=true&codpal=<%out.print(palestra.getCod());%>">Cancelar inscrição</a>

<%
        }
    }
%>
<%
    }
    else
    {
%>
        <p>Não existem eventos disponíveis!</p>    
<%
    }
%>