<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="model.Palestra"%>
<%@page import="model.Matricula"%>
<%@page import="model.Evento"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Evento> evts = null;  
    ArrayList<Palestra> pals = null;  
    Evento evt;
    Matricula mat;
    Palestra palestra;
%>
<%
    //session.invalidate();
    if(session.getAttribute("aluno") == null)
    {
        response.sendRedirect("ApplicationController");
    }
            
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
    
    if(session.getAttribute("pals") != null)
    {
        pals = (ArrayList<Palestra>)session.getAttribute("pals");                               
    }
    else
        pals = null;
    
    
    if(session.getAttribute("palestra") != null)
    {
        palestra = (Palestra)session.getAttribute("palestra");
    }
    else
        palestra = null;

    if(session.getAttribute("altered_mat") != null)
    {
        mat = (Matricula)session.getAttribute("altered_mat");
        evt = mat.getEvento();
    }
    else
        mat = null;        

%>

    <a href="AlunoController">Menu</a>
    <form action="MatriculaController?path=matricula.jsp" method="post">

<%
    if(mat != null && palestra != null)
    {
%>

    <h2>Matrícula <%out.print(mat.getCodigo());%> </h2>
    
    <p>Evento: <%out.print(mat.getEvento().getNome());%></p>
    <p>Palestra: <%out.print(palestra.getNome() + " <br> "+palestra.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));%>
<%
    }
%>

<%
    if(evts != null && evts.size() > 0)
    {
%>
        <p>Selecione Evento:</p>
        <select name="evento" <%out.print((mat != null) ? " readonly='readonly'": "");%>>
            
<%
        for(Evento e : evts)
        {
%>

            <option value="<%out.print(e.getCodigo());%>" 
                <%out.print((evt != null && evt.equals(e)) ? "selected": "");%>>
                <%out.print(e.getNome());%>
            </option>
            
<%
        }
%>
        </select>
        <input type="submit" name="bEvt" value="Selecionar">
    </form> 
        
<%
        if(pals != null) 
        {
%>
    <form action="MatriculaController?path=listagem.jsp?list=true" method="post">
        <p>Selecione Palestra</p>
        <input type="text" name="evento" hidden="hidden" value="<%out.print(evt.getCodigo());%>">
        <select name="palestra" <% out.print((mat != null) ? " readonly='readonly'": "");%>>
<%
            for (Palestra p : pals)
            {
%>
            <option value="<%out.print(p.getCod());%>" 
                <%out.print((p == palestra) ? "selected": "");%>>
                <%out.print(p.getNome());%></option>
<%
            }
%>
        </select>
        <input type="submit" name="bPal" value="Enviar">
    </form>

        
<%
        }
    }
    else    
    if(mat != null)
    {
%>
        <br>
        <br>
        <a href="MatriculaController?path=listagem.jsp&list=true&delete=true&codmat=<%out.print(mat.getCodigo());%>">Cancelar inscrição</a>

<%
    }
    
    else
    {
%>
%>
        <p>Não existem eventos disponíveis!</p>    
<%
    }
%>