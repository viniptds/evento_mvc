
<%@page import="dao.UsuarioDAO"%>
<%@page import="model.Usuario"%>
<%!
    Usuario u;
    UsuarioDAO usd = new UsuarioDAO();
    int cod = 0;
    String nome = "", login = "", senha = "";
%>


<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("AdminController");
    }    
        
    if(session.getAttribute("altered_user") != null)
    {
        u = (Usuario)session.getAttribute("altered_user");
        nome = u.getNome();
        login = u.getLogin();
        cod = u.getCodigo();
    }                   
    else
    {
        nome = login = senha = "";
    }

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <a href="<%out.print(application.getContextPath());%>/UsuarioController">Menu</a>
        
        <form action="<%out.print(application.getContextPath());
              %>/UsuarioController?path=listagem.jsp&list=true&coduser=<%
               out.print(u.getCodigo());%>" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            
            <label>Login: </label>
            <input type="text" name="login" <% if(session.getAttribute("altered_user") != null) 
                out.print("readonly='readonly'");%> required="required" value="<% out.print(login); %>">
            <br>
            
            <label>Senha: </label>
            <input type="password" name="senha" required="required">
            <br>
            
            <input type="submit" name="bChange" value="Enviar">
        </form>
<%
    if(session.getAttribute("altered_user") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/UsuarioController?path=listagem.jsp&coduser=<%
               out.print(u.getCodigo());%>&delete=true&list=true">Deletar</a>

<%
    }
%>
    </body>
</html>
