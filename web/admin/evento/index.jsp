
<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/EventoController?hd=2&path=perfil.jsp">Novo Evento</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/EventoController?hd=1&path=listagem.jsp&list=true">Listar Evento(s)</a>
            </li>  
        </ul>