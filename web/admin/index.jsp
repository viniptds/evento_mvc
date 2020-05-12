
<%@page import="model.Usuario"%>
<%!
    Usuario us;
%>

<%
    //session.invalidate();
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
    else
    {
        us = (Usuario)session.getAttribute("user");
    }
%>


        <template:get name="_header_template" />
        <h1>Controle Administrativo</h1>
        
        <p>Logado como: <i><% out.print(us.getLogin()); %> </i> </p>
        <p>Operações:</p>
        <ul>
            <li>
                <a href="../UsuarioController">Gerenciar Usuários</a>
            </li>
            <br>
            
            <li>
                <a href="../AdminController?path=evento">Gerenciar Eventos</a>
            </li>
            <br>
            
            <li>
                <a href="../AdminController?path=instrutor">Gerenciar Instrutor</a>
            </li>                               
            <br>
            
            <li>
                <a href="../AdmAlunoController">Gerenciar Alunos</a>
            </li>
        </ul>                
        
        
        <p> <a href="ApplicationController?action=logout">Sair</a></p>
    </body>
</html>
