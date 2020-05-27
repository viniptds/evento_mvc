
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

        <p>Operações:</p>
        <ul>
            <li>
                <a href="UsuarioController">Gerenciar Usuários</a>
            </li>
            <br>
            
            <li>
                <a href="EventoController">Gerenciar Eventos</a>
            </li>
            <br>
            
            <li>
                <a href="PalestraController">Gerenciar Palestras</a>
            </li>                               
            <br>
            
            <li>
                <a href="InstrutorController">Gerenciar Instrutor</a>
            </li>                               
            <br>
            
            <li>
                <a href="AdmAlunoController">Gerenciar Alunos</a>
            </li>
        </ul>                 
        
