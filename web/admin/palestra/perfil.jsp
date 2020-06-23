<%@page import="model.Aluno"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.Matricula"%>
<%@page import="model.Evento"%>
<%@page import="model.Instrutor"%>
<%@page import="dao.PalestraDAO"%>
<%@page import="model.Palestra"%>
<%!
    Palestra pal;
    Evento ev;
    PalestraDAO usd = new PalestraDAO();
    int cod = 0, cap;
    String nome = "", desc = "";
    LocalDate data;    
%>


<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("AdminController");
    }    
        
    if(session.getAttribute("altered_pal") != null)
    {
        pal = (Palestra)session.getAttribute("altered_pal");
        nome = pal.getNome();
        desc = pal.getDescricao();
        cap = pal.getCapacidade();
        cod = pal.getCod();        
        data = pal.getData();
    }                   
    else
    {
        pal = null;
        nome = desc = "";
    }
    
    if(session.getAttribute("altered_evento") != null)
    {
        ev = (Evento)session.getAttribute("altered_evento");
    }
    else
        ev = null;

%>
<%
    if(ev != null)
    {
%>      

        <a href="<%out.print(application.getContextPath()+"/PalestraController?path=listagem.jsp&list=true&evt="+ev.getCodigo());%>">Voltar</a>                        

<%
    }
    else
    {
%>
        
        <a href="PalestraController">Menu</a>
        
<%
    }
%>
        
        <form action="<%out.print(application.getContextPath());
              %>/PalestraController?path=listagem.jsp&list=true<%out.print((ev !=  null) ? "?evt="+ev.getCodigo() : "");%>" method="post">
            <label>Nome: </label><input type="text" autofocus="true" name="nome" value="<% out.print(nome); %>">
            <br>
            
            <label>Descrição: </label><textarea type="textarea" name="desc" rows="3" maxlength="500"><% out.print(desc); %></textarea>
            <br>
            
            <label>Capacidade: </label><input type="number" name="cap" value="<% out.print(""+cap); %>">            
            <br>
            
            <label>Data: </label><input type="date" name="data" value="<% out.print(""+data); %>">            
            <br>
            
            <input type="submit" name="bChange" value="Enviar">
        </form>
            
        <br>
        <br>
<%
    if(pal != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/PalestraController?path=listagem.jsp&codpal=<%
               out.print(pal.getCod());%>&delete=true&list=true">Deletar</a>
        
        <br>
        <br>
        Vagas Disponíveis: <% out.print(String.valueOf(pal.getCapacidade()-pal.getAlunos().size())); %>              
        <br>                
        <a href="AdmAlunoController?path=listagem.jsp&list=true&conf=true">Confirmar matrículas</a>
        <br>
<%
        if(pal.getAlunos() != null && pal.getAlunos().size()>0)
        {            
%>
        <a href="AdmAlunoController?path=listagem.jsp&list=true">Visualizar Participantes</a>
        <br>

<%         
        }
        if(pal.getInstruts() != null && pal.getInstruts().size()>0)
        {                     
%>           
        
        <a href="InstrutorController?path=listagem.jsp&list=true">Visualizar Instrutores</a>
        
<%
        }
        else
        {
%>        
        
<%
        }
    }
%>        