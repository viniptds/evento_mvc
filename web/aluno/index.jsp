<%@page import="model.Aluno"%>

<%!
    Aluno al = null;    
%>

<%        
    if(session.getAttribute("aluno") == null)
    {
        response.sendRedirect("ApplicationController");
    }
    else
    {
        al = (Aluno)session.getAttribute("aluno");
    }    
    

%>


       
<%
    if(al != null)
    {
%>

        <p>Ol�, <%out.println(al.getNome() != null ? al.getNome() : "");%>
        <p> <a href="ApplicationController?action=logout">Sair</a></p>
        <p>
            Fun��es do Aluno:
        </p>
        <ul>
            <li>Manuten��o</li>
        </ul>
<%
}
%>