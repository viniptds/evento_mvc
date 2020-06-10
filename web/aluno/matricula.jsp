<%!
    
%>
<%
    if(session.getAttribute("aluno") == null)
    {
        response.sendRedirect("ApplicationController");
    }
%>

<%
    if(request.getParameter("evento") == null)
    {        
%>

    <form action="matricula.jsp" method="post">
        <input type="text" >
    </form> 
    }
    else
%>