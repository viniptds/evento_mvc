<%-- 
    Document   : new.jsp
    Created on : 02/05/2020, 14:51:01
    Author     : viniciuspadovan
--%>

<%@page import="model.Aluno"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cidade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    ArrayList<Cidade> listcid;
    Usuario us;
    Aluno al;  
%>

<%
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("index");
    }
    else
    {
        us = (Usuario)session.getAttribute("user");
        listcid = new ArrayList();
                
        
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="index">Menu</a>
        <h1>Cadastro de novo Aluno</h1>
        
        <form action="aluno" method="post">
            
            <label>Nome: </label>
            <input type="text" name="nome" required="required" />
            <br>
            
            <label>Email: </label>
            <input type="text" name="email" required="required" />
            <br>
            
            <label>CPF: </label>
            <input type="text" name="cpf" required="required" />
            <br>
            
            <label>Data de Nascimento: </label>
            <input type="date" name="datanasc" required="required" />
            <br>
            
            <label>Endereço: </label>
            <input type="text" name="endereco" required="required" />
            <br>
            
            <label>Número: </label>
            <input type="text" name="numero" required="required" />
            <br>
            
            <label>Complemento: </label>
            <input type="text" name="compl" required="required" />
            <br>
            
            <label>CEP: </label>
            <input type="text" name="nome" required="required" />
            <br>
            
            <label>Cidade: </label>
            <select name="cidade">
<%for(Cidade c : listcid) {%>
                <option value="<%out.print(c.getCodigo()); %>"><% out.print(c.getNome()); %></option>
<%}%>
            </select>
            <br>
            
            <input type="submit" name="bSend" value="Cadastrar">            
            
        </form>
    </body>
</html>
