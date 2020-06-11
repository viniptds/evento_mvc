

<%@page import="model.Matricula"%>
<%@page import="java.util.ArrayList"%>

<%!
    ArrayList<Matricula> lmat;
    Matricula mat;    
    String search = "";
    int cod;
%>

<%
    
    if(session.getAttribute("user") == null)
    {        
        response.sendRedirect("ApplicationController");        
    }
    
    if(session.getAttribute("listaMat") != null)
    {
        lmat = (ArrayList<Matricula>)session.getAttribute("listaMat");
    }    
    
%>
            
        <a href="<%out.print(application.getContextPath());%>/AlunoController">Menu</a>                
        
        <form method="post" action="<%out.print(application.getContextPath());%>/MatriculaController?path=matricula.jsp">
            
            <label>Buscar: </label>
            <input type="text" name="search" value="<%out.print(search.length() > 0 ? search : "");%>">
            
            <input type="submit" name="bSearch" value="Buscar">
        </form>
            <a href="<%out.print(application.getContextPath());%>/MatriculaController?path=perfil.jsp">Nova Matricula</a>
            
        
<%
    if(lmat != null)
    {        
        if(lmat.size()>0)
        {
%>        
        <br>
        <table border="3">
            <tr>
                <td>Nome</td>
                <td>Status</td>                
            </tr>
            
<% 
            for(Matricula m : lmat)
            {
%>

            <tr>
                <td>
                    <a href="<%out.print(application.getContextPath());
                       %>/MatriculaController?path=matricula.jsp&codmat=<%
                        out.print(m.getCodigo()); %>"> 
                        <% out.print(m.getEvento().getNome()); %>
                    </a>
                </td>

                <td><% out.print((m.isConfirmado()) ? "Confirmado" : "Pendente"); %></td>                
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
        <p> Não há matrícula(s) para essa busca. </p>
<%      }
    }
%> 