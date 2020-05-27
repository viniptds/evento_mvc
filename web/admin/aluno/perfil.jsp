
<%@page import="model.UF"%>
<%@page import="dao.UFDAO"%>
<%@page import="dao.CidadeDAO"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.Aluno"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cidade"%>

<%!
    Aluno al;
    ArrayList<UF> uf_list = new ArrayList<>();
    CidadeDAO cd = new CidadeDAO();
    UFDAO ufd = new UFDAO();
    UF selected_uf = ufd.busca(1);
    ArrayList<Cidade> city_list = cd.listar(""+selected_uf.getCodigo(), "uf_codigo");
    
    public void setList(String codigo)
    {
        city_list = cd.listar(codigo, "uf_codigo");
    }
%>

<%        
    if(session.getAttribute("user") == null)
    {
        response.sendRedirect("ApplicationController");
    }
    else
    {
        uf_list = ufd.listar("");
        if(request.getParameter("uf") != null)
        {
            selected_uf = ufd.busca(Integer.parseInt(request.getParameter("uf")));
            city_list = cd.listar(""+selected_uf.getCodigo(), "uf_codigo");
        }
        
        if(session.getAttribute("altered_aluno") != null)
        {
            al = (Aluno)session.getAttribute("altered_aluno");
        }
        
    }
%>

        <div class="p-2">
            <a href="<%out.print(application.getContextPath());%>/AdmAlunoController">Menu</a>
                            
            <form method="post" action="<%out.print(application.getContextPath());%>/AdmAlunoController">
                <div class="form-group">
                    <label>Nome</label><br/>
                    <input type="text" name="nome" value="${erros.possuiMensagem ? param.nome : aluno.nome}" maxlength="20" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Email</label><br/>
                    <input type="text" name="email" value="${erros.possuiMensagem ? param.email : aluno.email}" maxlength="50" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>CPF: </label><br/>
                    <input type="text" name="cpf" value="${erros.possuiMensagem ? param.cpf : aluno.cpf}" maxlength="15" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Data de Nascimento</label><br/>
                    <input type="date" name="datanasc" value="${erros.possuiMensagem ? param.datanasc : aluno.datanasc}" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Endereco</label><br/>
                    <input type="text" name="endereco" value="${erros.possuiMensagem ? param.endereco : aluno.endereco}" maxlength="100" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Numero</label><br/>
                    <input type="text" name="numero" value="${erros.possuiMensagem ? param.numero : aluno.numero}" maxlength="5" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Complemento</label><br/>
                    <input type="text" name="complemento" value="${erros.possuiMensagem ? param.complemento : aluno.complemento}" maxlength="20" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>CEP</label><br/>
                    <input type="text" name="cep" value="${erros.possuiMensagem ? param.cep : aluno.cep}" maxlength="10" class="form-control"/>
                </div>
                
                
                <div class="form-group">
                    <label>UF</label><br/>
                    <select name="uf">
                        <%for(UF uf : uf_list) 
                        { 
                        %>
                        
                        <option value="<% out.print(uf.getCodigo()); %>" >
                                <%out.print(uf.getNome());%>
                        </option>
                        
                        <%
                        }
                        %>
                    </select>                           
                </div>
                    
                
                <div class="form-group">
                    <label>Cidade</label>
                    <br>
                    <select name="cidade">
                        <%for(Cidade c : city_list) 
                        {
                        %>
                        
                        <option value="<% out.print(c.getCodigo()); %>">
                                <%out.print(c.getNome());%>
                        </option>
                        
                        <%
                        }
                        %>
                    </select>                           
                </div>
                    
                <div class="form-group">
                    <label>Senha</label><br/>
                    <input type="password" name="senha" value="${erros.possuiMensagem ? param.senha : aluno.senha}" maxlength="10" class="form-control"/>
                </div>
                
                <div class="form-group mt-2">
                    <input type="submit" name="bSend" value="Cadastrar" class="btn btn-primary" ${alterando ? "disabled=\"disabled\"" : ""} />
                    
                </div>
            </form>            
        </div>
<% 
    if(session.getAttribute("altered_aluno") != null) 
    { 
%>
        
        <a href="<%out.print(application.getContextPath());
           %>/AdmAlunoController?path=listagem.jsp&cod=<%
               out.print(al.getCodigo());%>&delete=true&list=true">Deletar</a>
        <br>
        <br>
        
        <a href="">Visualizar Palestras do Aluno</a>
        
<%
}
%>
