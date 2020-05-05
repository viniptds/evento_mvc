/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aluno;
import persist.DAOException;
import util.Util;

/**
 *
 * @author viniciuspadovan
 */
@WebServlet(name = "AdmAlunoController", urlPatterns = {"/AdmAlunoController"})
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
            
            HttpSession session = request.getSession();
            
            if(session.getAttribute("user") == null)
                response.sendRedirect("ApplicationController");
            else
            {
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    response.sendRedirect(path);
                }
            }
            
//            
//        boolean inserindo = request.getParameter("bSend") != null;
//
//        if (inserindo) {
//            int cod = 0;
//            try {
//                cod = Integer.parseInt(request.getParameter("codigo"));
//            } catch (NumberFormatException ex) {
//                meusErros.addMensagem("Código não informado corretamente");
//            }
//            String nome = request.getParameter("nome");
//            if (Util.isEmpty(nome) || nome.length() > 20) {
//                meusErros.addMensagem("Nome não informado corretamente.");
//            }
//            String email = request.getParameter("email");
//            if (Util.isEmpty(email) || email.length() <=0 ) {
//                meusErros.addMensagem("Email não informado corretamente.");
//            }
//            String cpf = request.getParameter("cpf");
//            if (Util.isEmpty(cpf) || cpf.length() < 10 ) {
//                meusErros.addMensagem("CPF inválido");
//            }
//            LocalDate data = null;
//            if (request.getParameter("datanasc") != null) {
//                String dataN[];
//                
//                int dia, mes, ano;
//                dataN = request.getParameter("datanasc").split("/");
//                          try
//                          {
//                            dia = Integer.parseInt(dataN[0]);
//                            mes = Integer.parseInt(dataN[1]);
//                            ano = Integer.parseInt(dataN[2]);
//
//                            data = LocalDate.of(ano, mes, dia);
//                          }
//                          catch(NumberFormatException e)
//                          {
//                            meusErros.addMensagem("Data Inválida");
//                          }
//            }
//            else
//                meusErros.addMensagem("Data Inválida");
//            
//            String endereco = request.getParameter("endereco");
//            if (Util.isEmpty(endereco) || endereco.length() < 10 ) {
//                meusErros.addMensagem("Endereco inválido");
//            }
//            int num = 0;
//            if (request.getParameter("numero") != null) {
//                String numaux;
//                numaux = request.getParameter("numero");
//                num=Integer.parseInt(numaux);
//            }
//            else
//                meusErros.addMensagem("Numero inválido");
//            String complemento = request.getParameter("comp1");
//            if (Util.isEmpty(complemento) || complemento.length() < 0 ) {
//                meusErros.addMensagem("Complemento inválido");
//            }
//            String cep = request.getParameter("cep");
//            if (Util.isEmpty(cep) || cep.length() < 5 ) {
//                meusErros.addMensagem("CEP inválido");
//            }
//            String senha = request.getParameter("senha");
//            if (Util.isEmpty(senha) || senha.length() < 5 ) {
//                meusErros.addMensagem("Senha inválido");
//            }
//            if (meusErros.isEmpty()) {
//                try {
//                    if (inserindo) {
//                        Aluno al = new Aluno(cod, num, nome, email, senha, endereco, complemento, cep, cpf, data, cid);
//                        dao.insert(al);
//                    } else {
//                        Aluno temp = dao.busca(cod);
//                        if (temp == null) {
//                            meusErros.addMensagem("Registro não encontrado.");
//                        } else {
//                            temp.setNome(nome);
//                            temp.setNum(num);
//                            temp.setCep(cep);
//                            temp.setCidade(cid);
//                            temp.setComplemento(complemento);
//                            temp.setCpf(cpf);
//                            temp.setDatanasc(data);
//                            temp.setEmail(email);
//                            temp.setEndereco(endereco);
//                            temp.setSenha(senha);
//                            dao.update(temp);
//                        }
//                    }
//                } catch (DAOException ex) {
//                    meusErros.addMensagem(ex.getLocalizedMessage());
//                }
//            }
//        }
//
//        List<Aluno> listaDeCadastrados;
//        try {
//            listaDeCadastrados = dao.listar(null);
//        } catch (DAOException ex) {
//            listaDeCadastrados = null;
//        }
//        dao = null;
//
//        request.setAttribute("erros", meusErros);
//        request.setAttribute("cadastrados", listaDeCadastrados);
//
//        RequestDispatcher rd = request.getRequestDispatcher("new.jsp");
//        rd.forward(request, response);
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
