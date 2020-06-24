
<%@page import="model.Palestra"%>
<%@page import="dao.InstrutorDAO"%>
<%@page import="model.Instrutor"%>
<%!
    Palestra pal;
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

    if(session.getAttribute("altered_pal") != null)
    {
        pal = (Palestra)session.getAttribute("altered_pal");
    }
    else
    {
        pal = null;
        response.sendRedirect("PalestraController");
    }    
%>

<%
    if(pal != null)
    {
%>
        <a href="<%out.print(application.getContextPath()+"/PalestraController?hd=2&path=perfil.jsp&codpal="+pal.getCod());%>">Voltar</a>      
<%
    }
%>                  
        
        <form action="<%out.print(application.getContextPath());%>/InstrutorController?hd=1&path=listagem.jsp&list=true" method="post">            
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
        <br>
        <br> 
        <a href="<%out.print(application.getContextPath());
           %>/InstrutorController?hd=1&path=listagem.jsp&cod=<%
               out.print(in.getCodigo());%>&delete=true&list=true">Deletar desta Palestra</a>
        <br>   
        
               <a href="<%out.print(application.getContextPath());
           %>/InstrutorController?hd=1&path=listagem.jsp&cod=<%
               out.print(in.getCodigo());%>&delete=true&list=true&all=true">Deletar Permanentemente</a>
        <br>
        <br>                
        
<%
    }
%>
