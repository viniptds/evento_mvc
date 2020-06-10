
<%@page import="model.Aluno"%>
<%@page import="java.util.ArrayList"%>
<%!

    ArrayList<Aluno> alunos;
    String search = "";
%>
<%
    
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
    
    if(session.getAttribute("listaAluno") != null)
    {
        alunos = (ArrayList<Aluno>)session.getAttribute("listaAluno");
    }    
   
%>

        <a href="<%out.print(application.getContextPath());%>/AlunoController">Menu</a>                
        
        <form method="post" action="<%out.print(application.getContextPath());%>/AlunoController?path=listagem.jsp&list=true">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/AlunoController?path=perfil.jsp">Novo Aluno</a>
            
        
<%
    if(alunos != null)
    {        
        if(alunos.size()>0)
        {
%>        
        <br>
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Email</td>    
                <td>CPF</td>
            </tr>
            
<% 
            for(Aluno al : alunos)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/AlunoController?path=perfil.jsp&codaluno=<%
                        out.print(al.getCodigo()); %>"> 
                        <% out.print(al.getNome()); %>
                    </a>
                </td>

                <td><% out.print(al.getEmail()); %></td>   
                <td><% out.print(al.getCpf()); %></td>   
            </tr>            
<%
            }
%>
        </table>
<%
        }   
        else
        {
%>
        <p> Não há aluno(s) para essa busca. </p>
<%      }
    }
%> 