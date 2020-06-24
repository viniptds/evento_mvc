
<%@page import="model.Palestra"%>
<%@page import="java.time.format.DateTimeFormatter"%>
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
    
    Palestra pal;
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
        else
            al = null;
        
        if(session.getAttribute("altered_pal") != null)
        {
            pal = (Palestra)session.getAttribute("altered_pal");
        }
        else
            pal = null;
    }
%>
        
<script type="text/javascript" src="ajax-city.js"></script>
<%
    if(pal == null)
    {
%>        
        <a href="<%out.print(application.getContextPath());%>/AdminController">Menu</a>
                            
<%
    }
    else
    {
%>
        <a href="<%out.print(application.getContextPath()+"/PalestraController?hd=2&path=perfil.jsp&codpal="+pal.getCod());%>">Voltar</a>
<%
    }
%>
            <form method="post" action="<%out.print(application.getContextPath());%>/AdmAlunoController">
                <div class="form-group">
                    <label>Nome</label><br/>
                    <input type="text" name="nome" value="<%out.print((al != null) ? al.getNome() : "");%>" maxlength="20" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Email</label><br/>
                    <input type="text" name="email" value="<%out.print((al != null) ? al.getEmail() : "");%>" maxlength="50" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>CPF: </label><br/>
                    <input type="text" name="cpf" value="<%out.print((al != null) ? al.getCpf() : "");%>" maxlength="15" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Data de Nascimento</label><br/>
                    <input type="date" name="datanasc" value="<%out.print((al != null) ? al.getDatanasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");%>" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Endereco</label><br/>
                    <input type="text" name="endereco" value="<%out.print((al != null) ? al.getEndereco() : "");%>" maxlength="100" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Numero</label><br/>
                    <input type="text" name="numero" value="<%out.print((al != null) ? al.getNum() : "");%>" maxlength="5" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Complemento</label><br/>
                    <input type="text" name="complemento" value="<%out.print((al != null && al.getComplemento() != null) ? al.getComplemento() : "");%>" maxlength="20" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>CEP</label><br/>
                    <input type="text" name="cep" value="<%out.print((al != null) ? al.getCep() : "");%>" maxlength="10" class="form-control"/>
                </div>
                
                
                <div class="form-group">
                    <label>UF</label><br/>
                    <select name="uf" onchange="doCompletion()">
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
                    <input type="password" name="senha" maxlength="10" class="form-control"/>
                </div>
                
                <div class="form-group mt-2">
                    <input type="submit" name="bSend" value="Cadastrar" class="btn btn-primary" ${alterando ? "disabled=\"disabled\"" : ""} />
                    
                </div>
            </form>            
        </div>
<% 
    if(al != null) 
    { 
%>
        
        <a href="<%out.print(application.getContextPath());
           %>/AdmAlunoController?hd=1&path=listagem.jsp&cod=<%
               out.print(al.getCodigo());%>&delete=true&list=true">Deletar</a>                
        
<%
}
%>
