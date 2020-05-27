
<%@page import="dao.InstrutorDAO"%>
<%@page import="model.Instrutor"%>
<%!
    Instrutor in;
    InstrutorDAO insd = new InstrutorDAO();
    int cod = 0;
    String nome = "", curr = "";
%>

<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("ApplicationController");
    }    
        
    if(session.getAttribute("altered_inst") != null)
    {
        in = (Instrutor)session.getAttribute("altered_inst");
        nome = in.getNome();
        curr = in.getCurriculo();
        cod = in.getCodigo();        
    }                   
    else
    {
        nome = curr = "";
    }

%>

        <a href="<%out.print(application.getContextPath());%>/InstrutorController">Menu</a>
        
        <form action="<%out.print(application.getContextPath());%>/InstrutorController?path=listagem.jsp&list=true" method="post">            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" value="<% out.print(nome); %>">
            <br>
            
            <label>Currículo: </label>
            <input type="text" name="curr" required="required" value="<% out.print(curr); %>">
            <br>
                        
            <input type="submit" name="bChange" value="Enviar">
        </form>
<%
    if(session.getAttribute("altered_inst") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/InstrutorController?path=listagem.jsp&cod=<%
               out.print(in.getCodigo());%>&delete=true&list=true">Deletar</a>
        <br>
        <br>
        
        <a href="">Vincular a uma palestra</a>
        <br>
        
        <a href="">Visualizar palestras</a>
        
<%
    }
%>
