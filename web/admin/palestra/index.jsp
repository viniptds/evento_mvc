<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/PalestraController?path=perfil.jsp">Nova Palestra</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/PalestraController?path=listagem.jsp&list=true">Listar Palestra(s)</a>
            </li>  
        </ul>