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
        
        <p>
            Funções do Aluno:
        </p>
        <ul>
            <li><a href="matricula.jsp">Realizar Matrícula</a></li>
            <li><a href="">Visualizar</a></li>
        </ul>
<%
}
%>