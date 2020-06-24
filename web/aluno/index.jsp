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
            <li><a href="MatriculaController?hd=1&path=matricula.jsp&bEvt=true">Realizar Matrícula</a></li>
            <li><a href="MatriculaController?hd=2&path=listagem.jsp&list=true">Visualizar Matrículas</a></li>
        </ul>
<%
}
%>