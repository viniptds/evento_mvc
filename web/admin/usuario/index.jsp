
<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=perfil.jsp">Novo Usu�rio</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=listagem.jsp&list=true">Listar Usu�rio(s)</a>
            </li>  
        </ul>