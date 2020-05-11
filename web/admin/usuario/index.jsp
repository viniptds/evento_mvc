
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Menu Usuário</h1>
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/AdminController?path=usuario/perfil.jsp">Novo Usuário</a>
            </li>
            
            <li>
                <a href="<%out.print(application.getContextPath());%>/AdminController?path=usuario/listagem.jsp&list=true">Listar Usuários</a>
            </li>  
        </ul>
    </body>
</html>
