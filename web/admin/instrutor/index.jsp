
<%
    
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/InstrutorController?path=perfil.jsp">Novo Instrutor</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/InstrutorController?path=listagem.jsp&list=true">Listar Instrutor(es)</a>
            </li>  
        </ul>