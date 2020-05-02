<%@page import="model.Aluno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    Aluno al;
    //User us;
%>

<%    
    if (session.getAttribute("user") != null)
    {        
        response.sendRedirect("admin");        
    }
    if(session.getAttribute("aluno") != null)
    {
        al = (Aluno)session.getAttribute("aluno");
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        
<%
    if (session.getAttribute("user") == null && session.getAttribute("aluno") == null)
    {
%>
        <h1>Controlador de Eventos com MVC</h1>
        <p>Bem-vindo(a), faça login abaixo para acessar as opções:</p>
        <br>
        
        <form action="controller/index?action=login" method="post">

            <label>Acesso: </label>
            <select name="option">
                <option value="0">Aluno</option>
                <option value="1">Usuário</option>
            </select>
            <br>
            
            <label>Login: </label>
            <input type="text" name="login" required="required" placeholder="Usuário e/ou Email">
            <br>

            <label>Password: </label>
            <input type="password" name="password" required="required">
            <br>

            <input type="submit" name="bSubmit">        
        </form>
<%
}
else if(al != null)
{
%>

        <p>Olá, <%out.println(al.getNome() != null ? al.getNome() : "");%>
        <p> <a href="index?action=logout">Sair</a></p>
        <p>
            Funções do Aluno:
        </p>
        <ul>
            <li>Manutenção</li>
        </ul>
<%
}
%>
    </body>
</html>
