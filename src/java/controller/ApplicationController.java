package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.AlunoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aluno;
import model.Usuario;

/**
 *
 * @author viniciuspadovan
 */
public class ApplicationController extends HttpServlet {

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
            
            String login, password, option;
            Aluno al = null;
            Usuario us = null;
            
            AlunoDAO ald = new AlunoDAO();
            UsuarioDAO usd = new UsuarioDAO();
            
            HttpSession session =  request.getSession();                                                
            
            String action;
            
            //garantindo limpeza do atributo
            session.removeAttribute("altered_user");
            
            //RequestDispatcher rd;
            
            if(request.getParameter("action") != null)
            {
                action = request.getParameter("action");        
                
                if(action.equals("login"))
                {                    
                    login = request.getParameter("login");

                    if(login != null && login.length() > 0)
                    {
                        password = request.getParameter("password");

                        if(password != null && password.length() > 0)
                        {
                            option = request.getParameter("option");

                            switch(option)
                            {
                                case "0": 
                                    al = ald.login(login, password);

                                    if(al != null)
                                    {
                                        session.setAttribute("aluno", al);                                        
                                    }
                                    break;

                                case "1":                            
                                    us = usd.login(login, password);

                                    if(us != null)
                                    {
                                        session.setAttribute("user", us);                                             
                                    }   
                                    break;
                            }                                                       
                        }
                        else
                            System.out.println("Insira senha!");
                    }
                    else
                        System.out.println("Insira login");
                }
                else
                if(action.equals("logout"))
                {
                    session.removeAttribute("user");
                    session.removeAttribute("aluno");
                }
            }                        
            if(session.getAttribute("user") != null)
            {
//                rd = request.getRequestDispatcher("AdminController");
//                rd.forward(request, response);
                response.sendRedirect("admin");
            }
            else
            if(session.getAttribute("aluno") != null)
            {                
//                rd = request.getRequestDispatcher("AlunoController");
//                rd.forward(request, response);
                response.sendRedirect("aluno");                
            }
            else               
                response.sendRedirect(this.getServletContext().getContextPath()+"/index.jsp");
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
