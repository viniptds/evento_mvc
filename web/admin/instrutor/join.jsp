<%@page import="model.Instrutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Palestra"%>
<%!
    Palestra pal;
    ArrayList<Instrutor> insts = new ArrayList();
%>
<% 
    if(session.getAttribute("user") == null)
        response.sendRedirect("ApplicationController");
    
    if(session.getAttribute("altered_pal") != null)
    {
        pal = (Palestra)session.getAttribute("altered_pal");
    }
    else
    {
        pal = null;
        response.sendRedirect("PalestraController");
    }

    if(session.getAttribute("listaInst") != null)
    {
        insts = (ArrayList<Instrutor>)session.getAttribute("listaInst");
    }
    else
    {
        insts = null;
    }

    
%>

<%
    if(pal != null)
    {
%>
<a href="InstrutorController?hd=1&path=listagem.jsp&list=true"> Voltar</a>

<table border="3">
    <tr>
        <td>Nome</td>
        <td>Currículo</td>   
        <td>Vincular</td>
    </tr>
    
<%  
        for(Instrutor i : insts)
        {
%>
    <tr>
        <td><%out.print(i.getNome());%></td>
        <td><%out.print(i.getCurriculo());%></td>
        <td><a href="InstrutorController?hd=3&path=join.jsp&list=true&filter=true&join=true&codinst=<%out.print(i.getCodigo());%>">Adicionar</a></td>
    </tr>
<%
        }
    }
%>
        
</table>