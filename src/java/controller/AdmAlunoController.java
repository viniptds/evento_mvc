/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AlunoDAO;
import dao.CidadeDAO;
import dao.UFDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aluno;
import model.Cidade;
import model.Usuario;
import persist.DAOException;
import util.ConfigPagina;
import util.Erros;
import util.Util;

/**
 *
 * @author viniciuspadovan
 */
public class AdmAlunoController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {            
            
            String path;      
            String title [] = {"Controle de Alunos", "Perfil Aluno"};
            
            HttpSession session = request.getSession();
            Erros meusErros = new Erros();
            
            CidadeDAO cd = new CidadeDAO();
            UFDAO ufd = new UFDAO();
            AlunoDAO dao = new AlunoDAO();
            Aluno al = new Aluno();
            
            int cod = 0, num;
            String nome, email, senha, cpf, endereco, complemento = "", cep;
            LocalDate data;
            
            //verifica permissao
            if(session.getAttribute("user") == null)
                response.sendRedirect("ApplicationController");
            else
            {
                //coleta codigo para alteracao
                if(request.getParameter("codaluno") != null)
                {
                    try 
                    {
                        cod = Integer.parseInt(request.getParameter("codaluno"));

                        if(cod > 0)
                        {
                            al = dao.busca(cod);
                            session.setAttribute("altered_aluno", al);
                        }                        
                    }
                    catch(NumberFormatException ex)
                    {
                        System.out.println("Erro ao converter valor!");
                    }
                }
                
                //manipula aluno
                if(request.getParameter("bSend") != null)
                {
                    nome = request.getParameter("nome");
                    if (Util.isEmpty(nome) || nome.length() > 20)
                    {
                        meusErros.addMensagem("Nome não informado corretamente.");
                    }
                    
                    email = request.getParameter("email");
                    if (Util.isEmpty(email) || email.length() < 0) 
                    {
                        meusErros.addMensagem("Email não informado corretamente.");
                    }
                    
                    cpf = request.getParameter("cpf");
                    if (Util.isEmpty(cpf) || cpf.length() < 10 ) 
                    {
                        meusErros.addMensagem("CPF inválido");
                    }
                    
                    data = null;
                    if (request.getParameter("datanasc") != null) 
                    {
                        String dataN[];

                        int dia, mes, ano;
                        dataN = request.getParameter("datanasc").split("/");
                                  try
                                  {
                                    dia = Integer.parseInt(dataN[0]);
                                    mes = Integer.parseInt(dataN[1]);
                                    ano = Integer.parseInt(dataN[2]);

                                    data = LocalDate.of(ano, mes, dia);
                                  }
                                  catch(NumberFormatException e)
                                  {
                                    meusErros.addMensagem("Data Inválida");
                                  }
                    }
                    else
                        meusErros.addMensagem("Data Inválida");

                    endereco = request.getParameter("endereco");
                    if (Util.isEmpty(endereco) || endereco.length() < 10 ) 
                    {
                        meusErros.addMensagem("Endereco inválido");
                    }
                    
                    num = 0;
                    if (request.getParameter("numero") != null) {
                        String numaux;
                        numaux = request.getParameter("numero");
                        num=Integer.parseInt(numaux);
                    }
                    else
                        meusErros.addMensagem("Numero inválido");
                    
                    
                    if (request.getParameter("complemento") != null) 
                    {
                        complemento = request.getParameter("complemento");                        
                    }
                    
                    cep = request.getParameter("cep");
                    if (Util.isEmpty(cep) || cep.length() < 5 ) {
                        meusErros.addMensagem("CEP inválido");
                    }
                    senha = request.getParameter("senha");
                    if (Util.isEmpty(senha) || senha.length() < 5 ) {
                        meusErros.addMensagem("Senha inválido");
                    }

                    int cidstr = Integer.parseInt(request.getParameter("cidade"));
                    if (Util.isEmpty(cep) || cep.length() < 5 ) {
                        meusErros.addMensagem("CEP inválido");
                    }
                    
                    Cidade cid = cd.busca(cidstr);
                    if(cid == null)
                        meusErros.addMensagem("Cidade inválida");
                                        
                    
                    if (meusErros.isEmpty()) 
                    {                    
                        if(session.getAttribute("altered_aluno") == null)
                        {                                                                        
                            al = new Aluno(cod, num, nome, email, senha, endereco, complemento, cep, cpf, data, cid);
                            dao.insert(al);  
                            System.out.println("Inserido!");
                        }
                        else
                        {                            
                            al.setCep(cep);                                                        
                            al.setCidade(cid);
                            al.setComplemento(complemento);
                            al.setCpf(cpf);
                            al.setDatanasc(data);
                            al.setEmail(email);
                            al.setEndereco(endereco);
                            al.setNome(nome);
                            al.setNum(num);
                            al.setSenha(senha);                            
                            
                            dao.update(al);
                            session.removeAttribute("altered_aluno");
                            System.out.println("Alterado!");
                        }                                                                   
                    }                                         
                }                    
                 
                
                if(request.getParameter("delete") != null)
                {
                    al = (Aluno)session.getAttribute("altered_aluno");
                    if(request.getParameter("delete").equals("true") && al != null)
                    {                                                                        
                            dao.remove(al);
                            System.out.println("Removido!");                                                                        
                    }
                    
                    session.removeAttribute("altered_aluno");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("list") != null)
                {                                        
//                    if(request.getParameter("search") == null)
//                    {
//                        session.setAttribute("listaAluno", dao.list());
//                    }
//                    else
//                    {
//                        String search = request.getParameter("search");
//                        session.setAttribute("listaAluno", dao.search(search, "alu_email"));
//                    }                    
                }                                                       
                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/aluno/"+path, title[0], (Usuario)session.getAttribute("user")));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/aluno/"+path);
                }
                else
                {
                    request.setAttribute("configuracao", new ConfigPagina("/admin/aluno/index.jsp", title[0], (Usuario)session.getAttribute("user")));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/aluno/index.jsp");
                }                        
            
                
                    
                    

//        ArrayList<Aluno> listaDeCadastrados;
//        try {
//            listaDeCadastrados = dao.listar(null);
//        } catch (DAOException ex) {
//            listaDeCadastrados = null;
//        }
//        dao = null;
//
//        request.setAttribute("erros", meusErros);
//        request.setAttribute("cadastrados", listaDeCadastrados);

//        RequestDispatcher rd = request.getRequestDispatcher("new.jsp");
//        rd.forward(request, response);
            }
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
