/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.InstrutorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Instrutor;
import util.ConfigPagina;

/**
 *
 * @author viniciuspadovan
 */
public class InstrutorController extends HttpServlet {

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
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            
            HttpSession session = request.getSession();
            
            InstrutorDAO insd = new InstrutorDAO();            
            Instrutor in = null;
            
            String path;    
            String title [] = {"Menu Instrutor", "Listagem de Instrutor(es)", "Perfil"};
            
            String search, nome = "", curr = "";
            int cod = 0;
                                    
            session.removeAttribute("listaInst");
            
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

                        if(cod > 0)
                        {
                            in = insd.busca(cod);
                            session.setAttribute("altered_inst", in);                            
                        }
                        else
                            response.sendRedirect("listagem.jsp");
                    }
                    catch(NumberFormatException ex)
                    {
                        System.out.println("Erro ao converter valor!");
                    }
                }
                
                if(request.getParameter("bChange") != null)
                {
                    if(request.getParameter("nome") != null)
                    {
                        nome = request.getParameter("nome");

                        if(request.getParameter("curr") != null)
                        {
                            curr = request.getParameter("curr");
                            
                            if(nome.length() > 0 && curr.length() > 0)
                            {
                                in = new Instrutor(cod, nome, curr);
                                if(session.getAttribute("altered_inst") != null)
                                {                                
                                    insd.update(in);
                                    session.removeAttribute("altered_inst");
                                    System.out.println("Alterado!");
                                }
                                else
                                {
                                    insd.insert(in);          
                                    System.out.println("Inserido!");
                                }
                                cod = 0;
                                nome = curr = "";     
                                request.removeAttribute("bChange");
                            }
                        }
                    }
                }            
                
                if(request.getParameter("delete") != null)
                {
                    in = (Instrutor)session.getAttribute("altered_inst");
                    if(request.getParameter("delete").equals("true") && in != null)
                    {                                                                        
                            insd.remove(in);
                            System.out.println("Removido!");                    
                        
                    }
                    session.removeAttribute("altered_inst");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("list") != null)
                {
                    if(request.getParameter("search") == null)
                    {
                        session.setAttribute("listaInst", insd.listar());
                    }
                    else
                    {
                        search = request.getParameter("search");
                        session.setAttribute("listaInst", insd.search(search, "ins_nome"));
                    }
                }
                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/instrutor/"+path, title[0]));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/usuario/"+path);
                }                
                else
                {
                    request.setAttribute("configuracao", new ConfigPagina("/admin/instrutor/index.jsp", title[0]));
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
