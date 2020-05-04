<%-- 
    Document   : new.jsp
    Created on : 02/05/2020, 14:51:01
    Author     : viniciuspadovan
--%>

<%@page import="java.lang.System.out"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.Aluno"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cidade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    ArrayList<Cidade> listcid;
    Usuario us;
    Aluno al;  
    AlunoDAO ald;
    int cod, num, dia, mes, ano;
    String nome="", email="", senha="", endereco="", complemento="", cep="", cpf="", dataN[], numaux;
    Cidade cid;
    LocalDate data;
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
        al = (Aluno)session.getAttribute("aluno");
        cod=al.getCodigo();
        num=al.getNum();
        nome=al.getNome();
        email=al.getEmail();
        senha=al.getSenha();
        endereco=al.getEndereco();
        complemento=al.getComplemento();
        cep=al.getCep();
        cpf=al.getCpf();
        data=al.getDatanasc();
        cid=al.getCidade();
    }
    if(request.getParameter("bSend") != null)
        {
            if(request.getParameter("nome") != null)
            {
                nome = request.getParameter("nome");
                
                if(request.getParameter("email") != null)
                {
                    email = request.getParameter("email");
                    
                    if(request.getParameter("cpf") != null)
                    {
                        cpf = request.getParameter("cpf");
                        
                        if(request.getParameter("datanasc") != null)
                        {
                            dataN = request.getParameter("datanasc").split("/");
                          try
                          {
                            dia = Integer.parseInt(dataN[0]);
                            mes = Integer.parseInt(dataN[1]);
                            ano = Integer.parseInt(dataN[2]);

                            data = LocalDate.of(ano, mes, dia); 
                              
                              
                            if(request.getParameter("endereco") != null)
                            {
                                endereco = request.getParameter("endereco");
                                
                                if(request.getParameter("numero") != null)
                                {
                                    
                                    numaux = request.getParameter("numero");
                                    num=Integer.parseInt(numaux);
                                    
                                    if(request.getParameter("comp1") != null)
                                    {
                                        complemento = request.getParameter("comp1");
                        
                                        if(request.getParameter("cep") != null)
                                        {
                                            cep = request.getParameter("cep");
                                            
                                            
                                            if(nome.length() > 0 && email.length() > 0 && cpf.length() > 0 && data != null && endereco.length()>0 &&
                                                    num >= 0 && complemento.length()>0 && cep.length()>0 )
                                            {
                                                al = new Aluno(cod, num, nome, email, senha, endereco, complemento, cep, cpf, data, cid);
                                                if(session.getAttribute("altered_aluno") != null)
                                                {                                
                                                    ald.update(al);
                                                }
                                                else
                                                {
                                                    ald.insert(al);
                                                    System.out.println("Inserido com sucesso");
                                                }
                                                    //response.sendRedirect("listagem.jsp");                            
                                            }
                                        }
                                    }
                                }
                        
                            }
                          }
                          catch(NumberFormatException e)
                          {
                            out.print("<script> alert('Data Inválida') </script>");
                          } 
                        }
                    }
                }
            }
        }
        
        
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
            <input type="text" name="cep" required="required" />
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
