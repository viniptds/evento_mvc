<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="model.Aluno"%>
<%@page import="model.Instrutor"%>
<%@page import="model.Matricula"%>
<%@page import="model.Palestra"%>
<%@page import="model.Evento"%>
<%!
Evento evt;
Palestra pal;
Aluno al;
Instrutor ins;
%>

<%

evt = (Evento)session.getAttribute("altered_evento");
//pal = (Palestra) session.getAttribute("pal_r");

%>



<%
if(evt == null)
{
%>
    <a href="EventoController?path=perfil.jsp&list=true&codevt=<%out.print(evt.getCodigo());%>">Voltar</a>
    <br>
    Evento não encontrado!
    
<%
}
else
{
%>
    <a href="EventoController?path=perfil.jsp&list=true&codevt=<%out.print(evt.getCodigo());%>">Voltar</a>

    <h1><%out.print(evt.getNome());%></h1>

<%
    if(evt.getPals() != null && evt.getPals().size() > 0)
    {
    
%>
<h3>
    Palestras:
</h3>
<ul>
<%  
        for(Palestra p : evt.getPals())
        {
%>
    <li>
        <%out.print(p.getNome() + " - " + p.getDescricao());%>
        <p> Data: <%out.print(p.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));%> </p>
        <p> Capacidade: <%out.print(p.getCapacidade());%> </p>
        <p> Livre: <%out.print(p.getCapacidade()-p.getAlunos().size());%> </p>
        <p> Ocupado: <%out.print(p.getAlunos().size());%>        
        
<%
        if(p.getInstruts() != null && p. getInstruts().size() > 0)
        {
%>
        <h4>Instrutor(es): </h4>
        <ul>
<%
            for(Instrutor in : p.getInstruts())
            {      
%>
            
            <li> 
                <%out.print(in.getNome() + " - " + in.getCurriculo());%>                
            </li>
            
<%
            }
%>
        </ul>
<%
        }
        else
        {
%>
            <br><br>
            Não há instrutores atribuídos!
<%
        }
%>
<%
        if(p.getAlunos()!= null && p. getAlunos().size() > 0)
        {
%>
        <h4>Inscrito(s):</h4>
        <ul>
<%
            for(Aluno al : p.getAlunos())
            {
                    
%>
            <li> <%out.print(al.getNome() + " - " + al.getCpf());%>
                
            </li>
<%
                
            }
%>
        </ul>        
<%
        }
        else
        {
%>
            <br><br>
            Não há inscritos nesta palestra!
<%
        }
%>
    </li>
    <hr>
<%
        }
%>
</ul>
<%
    }
    else
    {
%>
    <br>
    Não há palestras cadastradas!

<%
    }
}
%>