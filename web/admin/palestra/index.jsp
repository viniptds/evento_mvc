<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
    if(session.getAttribute("altered_evento") != null)
        response.sendRedirect("AdminController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/PalestraController?hd=2&path=perfil.jsp">Nova Palestra</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/PalestraController?hd=1&path=listagem.jsp&list=true">Listar Palestra(s)</a>
            </li>  
        </ul>