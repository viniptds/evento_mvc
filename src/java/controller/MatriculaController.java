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
            String title [] = {"Página Inicial", "Perfil"};  
            
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
//            session.removeAttribute("listaEvt");
            session.removeAttribute("evts");
            session.removeAttribute("evento");
            
            if(al == null)
            {
                response.sendRedirect("ApplicationController");
            }
            else
            {
                if(request.getParameter("codmat") != null)
                {
                    try 
                    {
                        codmat = Integer.parseInt(request.getParameter("codmat"));

                        m = matd.get(codmat);
                        
                        if(m != null)
                        {                                                        
                            session.setAttribute("altered_mat", m);
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
                    
                    request.removeAttribute("codmat");                    
                }
                
                
                
                if(request.getParameter("bEvt") != null)
                {                    
                    session.setAttribute("listaEvt", evtd.listar(true, al.getCodigo()));
                }
                
                if(request.getParameter("evento") != null)
                {
                    try
                    {
                        codevt = Integer.valueOf(request.getParameter("evento"));
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
                    if(session.getAttribute("palestra") != null && session.getAttribute("evento") != null)                        
                    {
                        evt = (Evento)session.getAttribute("evento");
                        pal = (Palestra)session.getAttribute("palestra");
                        
                        if(session.getAttribute("altered_mat") != null)
                        {
                            matd.update(m.getCodigo(), evt, pal);
                            session.removeAttribute("altered_mat");
                        }
                        else
                        {
                            matd.insert(new Matricula(codpal, al, evt, pal, false);
                        }
                        session.removeAttribute("palestra");
                        session.removeAttribute("evento");
                    }
//                    if(request.getParameter("nome") != null)
//                    {
//                        nome = request.getParameter("nome");
//
//                        if(request.getParameter("desc") != null)
//                        {
//                            desc = request.getParameter("desc");
//
//                            if(request.getParameter("cap") != null)
//                            {
//                                try
//                                {                                                                    
//                                    cap = Integer.parseInt(request.getParameter("cap"));
//                                }
//                                catch(NumberFormatException ex)
//                                {
//                                    err.addMensagem("Valor inválido para CAPACIDADE");
//                                }
//                                
//                                if(request.getParameter("data") != null)
//                                {
//                                    try
//                                    {
//                                        data = LocalDate.parse(request.getParameter("data").toString());
//                                    }
//                                    catch(DateTimeParseException ex)
//                                    {
//                                        err.addMensagem("Data inválida");
//                                    }
//                                
//                                    if(nome.length() > 0 && desc.length() > 0 && cap > 0 && data.isBefore(LocalDate.now()))
//                                    {                                    
//                                        if(session.getAttribute("altered_pal") != null)
//                                        {
//
//                                            p = pald.get(cod);
//                                            p.setNome(nome);
//                                            p.setCapacidade(cap);
//                                            p.setDescricao(desc);
//                                            if(!pald.update(p))
//                                            {
//                                                err.addMensagem("Erro ao alterar!");
//                                            }                                        
//                                            else
//                                            {
//                                                session.removeAttribute("altered_pal");                                                
//                                            }                                        
//                                        }
//                                        else
//                                        {
//                                            p = new Palestra(cod, nome, desc, cap, data, null, null, null);
//                                            if(!pald.insert(p))
//                                            {
//                                                err.addMensagem("Erro ao inserir");                                            
//                                            }
//                                        }
//                                        cod = cap = 0;
//                                        nome = desc = "";                                                           
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
                    
                if(request.getParameter("delete") != null)
                {                                                                              
                    if(request.getParameter("delete").equals("true") && m != null)
                    {                                                                           
                        matd.remove(m);
                        System.out.println("Removido!");                                                                   
                    }
                    session.removeAttribute("altered_mat");
                    //response.sendRedirect("ApplicationController");
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
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/"+path, title[0], ((Aluno)session.getAttribute("aluno")).getNome()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                }
                else
                {
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/aluno/index.jsp");            
                    request.setAttribute("configuracao", new ConfigPagina("/aluno/index.jsp", title[0], ((Aluno)session.getAttribute("aluno")).getNome()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
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
