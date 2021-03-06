/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import util.ConfigPagina;
import util.Erros;

/**
 *
 * @author viniciuspadovan
 */
public class UsuarioController extends HttpServlet {

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
            
            Erros err = new Erros();
            
            HttpSession session = request.getSession();
            
            UsuarioDAO usd = new UsuarioDAO();
            
            Usuario u = null;
            String path;    
            String title [] = {"Menu Usuário", "Listagem de Usuário", "Perfil"};
            
            String search, nome = "", senha = "", login = "";
            int cod = 0;
                                    
            session.removeAttribute("listaUser");
            
            int hd;
            try
            {
                hd = Integer.parseInt(request.getParameter("hd"));
                hd = (hd<title.length) ? hd: 0;
            }
            catch(NumberFormatException ex)
            {
                hd = 0;
            }                        
            
            if(session.getAttribute("user") == null)
            {
                response.sendRedirect("ApplicationController");
            }
            else
            {
                if(request.getParameter("coduser") != null)
                {
                    try 
                    {
                        cod = Integer.parseInt(request.getParameter("coduser"));

                        u = usd.get(cod);
                        
                        if(u != null)
                        {                                                        
                            session.setAttribute("altered_user", u);
                        }
                        else
                        {
                            err.addMensagem("Código inválido");
                            
                            response.sendRedirect("listagem.jsp");
                        }
                    }
                    catch(NumberFormatException ex)
                    {
                        err.addMensagem("Erro ao converter valor!");
                    }
                    
                    request.removeAttribute("coduser");                    
                }
                
                if(request.getParameter("bChange") != null)
                {
                    if(request.getParameter("nome") != null)
                    {
                        nome = request.getParameter("nome");

                        if(request.getParameter("login") != null)
                        {
                            login = request.getParameter("login");

                            if(request.getParameter("senha") != null)
                            {
                                senha = request.getParameter("senha");

                                if(nome.length() > 0 && login.length() > 0 && senha.length() > 0)
                                {                                    
                                    if(session.getAttribute("altered_user") != null)
                                    {
                                        
                                        u = usd.get(cod);
                                        u.setNome(nome);
                                        u.setSenha(senha);
                                        
                                        if(!usd.update(u))
                                        {
                                            err.addMensagem("Erro ao alterar!");
                                        }                                        
                                        else
                                        {
                                            session.removeAttribute("altered_user");                                                
                                        }                                        
                                    }
                                    else
                                    {
                                        u = new Usuario(cod, nome, login, senha);
                                        if(!usd.insert(u))
                                        {
                                            err.addMensagem("Erro ao inserir");                                            
                                        }
                                    }
                                    cod = 0;
                                    nome = login = senha = "";                                                           
                                }
                            }
                        }
                    }
                }
                
                if(request.getParameter("delete") != null)
                {                                        
                    u = (Usuario)session.getAttribute("altered_user");
                    System.out.println(u.getCodigo());
                    if(request.getParameter("delete").equals("true") && u != null)
                    {                               
                        System.out.println("-----");
                        if(!u.getLogin().equals("admin"))
                        {
                            usd.remove(u);
                            System.out.println("Removido!");                    
                        }

                        if(u.getLogin().equals(((Usuario)session.getAttribute("user")).getLogin()))
                            session.removeAttribute("user");    
                    }
                    session.removeAttribute("altered_user");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("list") != null)
                {
                    if(request.getParameter("search") == null)
                    {
                        session.setAttribute("listaUser", usd.list());
                    }
                    else
                    {
                        search = request.getParameter("search");
                        session.setAttribute("listaUser", usd.search(search, "usu_login"));
                    }
                }
                
                
                
                if(request.getParameter("path") != null)
                {                    
                    path = request.getParameter("path");
                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/usuario/"+path, title[hd], ((Usuario)session.getAttribute("user")).getLogin()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/usuario/"+path);                    
                }                
                else
                {
                    request.setAttribute("configuracao", new ConfigPagina("/admin/usuario/index.jsp", title[hd], ((Usuario)session.getAttribute("user")).getLogin()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/usuario/index.jsp");  
                }
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
