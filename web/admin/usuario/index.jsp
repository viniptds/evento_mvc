
<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?hd=2&path=perfil.jsp">Novo Usuário</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?hd=1&path=listagem.jsp&list=true">Listar Usuário(s)</a>
            </li>  
        </ul>