
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%!
    Usuario us;
    UsuarioDAO usd = new UsuarioDAO();
%>

<%
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
    else
    {
        us = (Usuario)session.getAttribute("user");        
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
                        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/AdmAlunoController?path=perfil.jsp">Cadastrar Aluno</a>
            </li>
            <li>
                <a href="">Listar Aluno(s)</a>
            </li>
        </ul>