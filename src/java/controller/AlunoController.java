/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aluno;
import model.Usuario;
import util.ConfigPagina;

/**
 *
 * @author viniciuspadovan
 */
public class AlunoController extends HttpServlet {

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
            String title [] = {"Página Inicial", "Perfil"};  
            
            HttpSession session = request.getSession();
            
            session.removeAttribute("altered_mat");
            
            if(session.getAttribute("aluno") == null)
            { 
                response.sendRedirect("ApplicationController");
            }
            else
            {            
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    //response.sendRedirect(path);
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/"+path, title[0], ((Aluno)session.getAttribute("aluno")).getNome()));
                }
                else
                {
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/aluno/index.jsp");            
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/index.jsp", title[0], ((Aluno)session.getAttribute("aluno")).getNome()));
                }
                RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                rd.forward(request, response);
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
