<%@page import="model.Instrutor"%>
<%@page import="dao.PalestraDAO"%>
<%@page import="model.Palestra"%>
<%!
    Palestra p;
    PalestraDAO usd = new PalestraDAO();
    int cod = 0, cap;
    String nome = "", desc = "";
%>


<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("AdminController");
    }    
        
    if(session.getAttribute("altered_pal") != null)
    {
        p = (Palestra)session.getAttribute("altered_pal");
        nome = p.getNome();
        desc = p.getDescricao();
        cap = p.getCapacidade();
        cod = p.getCod();        
    }                   
    else
    {
        nome = desc = "";
    }

%>

        <a href="PalestraController">Menu</a>
        <form action="<%out.print(application.getContextPath());
              %>/PalestraController?path=listagem.jsp&list=true" method="post">
            <label>Nome: </label><input type="text" autofocus="true" name="nome" value="<% out.print(nome); %>">
            <br>
            
            <label>Descrição: </label><input type="text" name="desc" value="<% out.print(desc); %>">
            <br>
            
            <label>Capacidade: </label><input type="number" name="cap" value="<% out.print(""+cap); %>">            
            <br>
            
            <input type="submit" name="bChange" value="Enviar">
        </form>
            
        <br>
        <br>
<%
    if(session.getAttribute("altered_pal") != null)
    {            
%>

        <a href="<%out.print(application.getContextPath());
           %>/PalestraController?path=listagem.jsp&codpal=<%
               out.print(p.getCod());%>&delete=true&list=true">Deletar</a>
        
        <br>
        <br>
        Vagas Disponíveis: <% out.print(String.valueOf(p.getCapacidade()-p.getAlunos().size())); %>
        
        <a href="">Visualizar Participantes</a>
        <br>
        <% 
        if(p.getInstruts() != null)
        {
                     
        %>           
        <a href="InstrutorController?path=listagem.jsp&codpal=<%
               out.print(p.getCod());%>&list=true">Visualizar Instrutores</a>
        
        <%
        }
        else
        {
        %>        
        
        <%
        }
            if(p.getEvento() == null)
        {
        %>
                
        
        <%
        }
    }
%>        