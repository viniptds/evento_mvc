/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EventoDAO;
import dao.MatriculaDAO;
import dao.PalestraDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Aluno;
import model.Evento;
import model.Matricula;
import model.Palestra;
import util.ConfigPagina;
import util.Erros;

/**
 *
 * @author viniciuspadovan
 */
public class MatriculaController extends HttpServlet {

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
            
            String path; 
            String title [] = {"Página Inicial", "Matrícula", "Minhas Matrículas"};  
            
            MatriculaDAO matd = new MatriculaDAO();                        
            EventoDAO evtd = new EventoDAO();
            PalestraDAO pald = new PalestraDAO();
            
            int codmat = 0, codevt = 0, codpal = 0;
            String search = "";         
            
            Matricula m = null;
            Aluno al = (Aluno) session.getAttribute("aluno");            
            Evento evt;
            Palestra pal;
            
            session.removeAttribute("listaMat");
            session.removeAttribute("listaEvt");            
            session.removeAttribute("evento");
            session.removeAttribute("pals");
            session.removeAttribute("palestra");
            
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
            
            if(al != null)
            {
                if(request.getParameter("codmat") != null)
                {
                    try 
                    {
                        codmat = Integer.parseInt(request.getParameter("codmat"));

                        m = matd.get(codmat);
                        pal = pald.getByMatricula(codmat);
                        
                        session.setAttribute("altered_mat", m);
                        session.setAttribute("palestra", pal);
                        
                    }
                    catch(NumberFormatException ex)
                    {
                        err.addMensagem("Erro ao converter valor!");
                    }
                    
                    request.removeAttribute("codmat");                    
                }
                
                
                
                if(request.getParameter("bEvt") != null)
                {                    
                    session.setAttribute("listaEvt", evtd.listar(true));
                }
                
                if(request.getParameter("evento") != null)
                {                    
                    try
                    {
                        codevt = Integer.valueOf(request.getParameter("evento"));
                        
                        ArrayList<Matricula> mats = matd.getAll(al.getCodigo());                                        
                        ArrayList<Palestra> pals = new ArrayList<Palestra>();
                        
                        
                        for(Palestra p : pald.search(3, "eve_codigo", ""+codevt))
                            if(!p.getAlunos().contains(al))
                                pals.add(p);
                        
                        session.setAttribute("pals", pals);
                        session.setAttribute("evento", evtd.busca(codevt));
                    }
                    catch(NumberFormatException ex)
                    {
                        System.out.println("Erro ao converter!");
                    }
                }
                
                if(request.getParameter("palestra") != null)
                {
                    try
                    {
                        codpal = Integer.valueOf(request.getParameter("palestra"));
                        session.setAttribute("palestra", pald.get(codpal));
                    }
                    catch(NumberFormatException ex)
                    {
                        System.out.println("Erro ao converter!");
                    }
                }
                
                if(request.getParameter("bPal") != null)
                {
                    evt = (Evento)session.getAttribute("evento");
                    pal = (Palestra)session.getAttribute("palestra");
                    
                    if(evt != null && pal != null)                        
                    {                        
                        
                        
                        if(session.getAttribute("altered_mat") != null)
                        {
                            matd.update(m.getCodigo(), evt, pal);
                            session.removeAttribute("altered_mat");
                        }
                        else
                        {
                            m = new Matricula(0, al, false, evt);
                            System.out.println(m);
                            matd.insert(m);
                            System.out.println("***");
                            matd.addMatricula(m, pal.getCod());
                        }
                        
                        session.removeAttribute("palestra");   
                        session.removeAttribute("evento");
                    }
//                   
                }
                    
                if(request.getParameter("delete") != null)
                {                                                                              
                    if(request.getParameter("delete").equals("true") && m != null)
                    {                                                                           
                        matd.remove(m);
                        System.out.println("Removido!");                                                                   
                    }
                    session.removeAttribute("altered_mat");                    
                }
                
                if(request.getParameter("list") != null)
                {
                    ArrayList<Matricula> mats = null;
                    
                    if(request.getParameter("search") == null)
                    {
                        mats = matd.getAll(al.getCodigo());                            
                    }
                    else
                    {
                        search = request.getParameter("search");
                        //mats = matd.search("pal_nome", search);                                                
                    }
                                        
//                    if(request.getParameter("evt") != null)
//                    {
//                        pals = pald.search("eve_codigo", ""+evt.getCodigo());                                                
//                        
//                    }
                    
                    session.setAttribute("listaMat", mats);
                }
                
                
                
                                                                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    //response.sendRedirect(path);                    
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/"+path, title[hd], ((Aluno)session.getAttribute("aluno")).getNome()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                }
                else
                {
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/aluno/index.jsp");            
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/index.jsp", title[hd], ((Aluno)session.getAttribute("aluno")).getNome()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                }
            }
            else
            {
                response.sendRedirect("ApplicationController");
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
