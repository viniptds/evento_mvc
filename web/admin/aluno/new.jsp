
<%@page import="java.lang.System.out"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.Aluno"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cidade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="p-2">
            <a href="index">Menu</a>
            <h1>Cadastro de novo Aluno</h1>
            <c:if test="${erros.possuiMensagem}">
                <div class="bg-warning p-1 border border-danger">
                    <p class="h3 text-center">Erros</p>
                    <ul>
                        <c:forEach var="msg" items="${erros.mensagens}">
                            <li>${msg}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <form method="post" action="new.do">
                <div class="form-group">
                    <label>Código</label><br/>
                    <input type="text" name="codigo" value="${erros.possuiMensagem ? param.codigo : aluno.codigo}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Nome</label><br/>
                    <input type="text" name="nome" value="${erros.possuiMensagem ? param.nome : aluno.nome}" maxlength="20" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Email</label><br/>
                    <input type="text" name="email" value="${erros.possuiMensagem ? param.email : aluno.email}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>CPF: </label><br/>
                    <input type="text" name="cpf" value="${erros.possuiMensagem ? param.cpf : aluno.cpf}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Data de Nascimento</label><br/>
                    <input type="text" name="datanasc" value="${erros.possuiMensagem ? param.datanasc : aluno.datanasc}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Endereco</label><br/>
                    <input type="text" name="endereco" value="${erros.possuiMensagem ? param.endereco : aluno.endereco}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Numero</label><br/>
                    <input type="text" name="numero" value="${erros.possuiMensagem ? param.numero : aluno.numero}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>Complemento</label><br/>
                    <input type="text" name="complemento" value="${erros.possuiMensagem ? param.complemento : aluno.complemento}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>CEP</label><br/>
                    <input type="text" name="cep" value="${erros.possuiMensagem ? param.cep : aluno.cep}" maxlength="2" class="form-control"/>
                </div>
                <div class="form-group mt-2">
                    <input type="submit" name="bSend" value="Cadastrar" class="btn btn-primary" ${alterando ? "disabled=\"disabled\"" : ""} />
                    
                </div>
            </form>
            <p class="mt-2">
                <a href="index.jsp" title="Menu" class="btn btn-secondary">Voltar</a>
            </p>
        </div>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>