
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

        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
                        
        <ul>
            <li>
                <a href="<%out.print(application.getContextPath());%>/AdmAlunoController?hd=2&path=perfil.jsp">Cadastrar Aluno</a>
            </li>
            <li>
                <a href="AdmAlunoController?hd=1&path=listagem.jsp?list=true&full=true">Listar Aluno(s)</a>
            </li>
        </ul>