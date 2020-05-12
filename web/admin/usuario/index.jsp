
<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <template:get name="_header_template" />
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        <h1>Menu Usuário</h1>
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=perfil.jsp">Novo Usuário</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/UsuarioController?path=listagem.jsp&list=true">Listar Usuário(s)</a>
            </li>  
        </ul>
    </body>
</html>
