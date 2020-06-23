/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EventoDAO;
import dao.PalestraDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Evento;
import model.Palestra;
import model.Usuario;
import util.ConfigPagina;
import util.Erros;

/**
 *
 * @author viniciuspadovan
 */
public class PalestraController extends HttpServlet {

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
            PalestraDAO pald = new PalestraDAO();
            
            Evento evt = (Evento)session.getAttribute("altered_evento");            
            EventoDAO evtd = new EventoDAO();
            
            
            int cod=0, cap= 0, codevt;
            String nome, desc, search = "";
            LocalDate data = LocalDate.now();
            
            Palestra p = null;           
            
            session.removeAttribute("listaPal");
            session.removeAttribute("altered_aluno");
            session.removeAttribute("altered_inst");
            
            if(session.getAttribute("user") == null)
            {
                response.sendRedirect("ApplicationController");
            }
            else
            {
                if(request.getParameter("codpal") != null)
                {
                    try 
                    {
                        cod = Integer.parseInt(request.getParameter("codpal"));

                        p = pald.get(cod);
                        
                        if(p != null)
                        {                                                        
                            session.setAttribute("altered_pal", p);
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
                    
                    request.removeAttribute("codpal");                    
                }
                else
                {
                    session.removeAttribute("altered_pal");
                }
                
                if(request.getParameter("bChange") != null)
                {
                    if(request.getParameter("nome") != null)
                    {
                        nome = request.getParameter("nome");

                        if(request.getParameter("desc") != null)
                        {
                            desc = request.getParameter("desc");

                            if(request.getParameter("cap") != null)
                            {
                                try
                                {                                                                    
                                    cap = Integer.parseInt(request.getParameter("cap"));
                                }
                                catch(NumberFormatException ex)
                                {
                                    err.addMensagem("Valor inválido para CAPACIDADE");
                                }
                                
                                if(request.getParameter("data") != null)
                                {
                                    try
                                    {
                                        data = LocalDate.parse(request.getParameter("data").toString());
                                    }
                                    catch(DateTimeParseException ex)
                                    {
                                        err.addMensagem("Data inválida");
                                    }
                                    
                                    if(nome.length() > 0 && desc.length() > 0 && cap > 0 && data.isBefore(LocalDate.now()))
                                    {    
                                        System.out.println("-");
                                        if(evt != null)
                                        {
                                            
                                            if(session.getAttribute("altered_pal") != null)
                                            {
                                                p = pald.get(cod);
                                                p.setNome(nome);
                                                p.setCapacidade(cap);
                                                p.setDescricao(desc);
                                                if(!pald.update(p))
                                                {
                                                    err.addMensagem("Erro ao alterar!");
                                                }                                        
                                                else
                                                {
                                                    session.removeAttribute("altered_pal");                                                
                                                }                                        
                                            }
                                            else
                                            {
                                                p = new Palestra(cod, nome, desc, cap, data, null, null);
                                                p.setCodevt(evt.getCodigo());
                                                if(!pald.insert(p))
                                                {
                                                    System.out.println("Erro ao inserir");                                            
                                                }
                                                evt.getPals().add(p);
                                                evtd.update(evt);

                                            }
                                        }
                                                                                
                                        cod = cap = 0;
                                        nome = desc = "";
                                    }
                                }
                            }
                        }
                    }
                }
                
                if(request.getParameter("delete") != null)
                {                                        
                    p = (Palestra)session.getAttribute("altered_pal");                    
                    if(request.getParameter("delete").equals("true") && p != null)
                    {                                                                           
                        pald.remove(p);
                        System.out.println("Removido!");                                                                   
                    }
                    session.removeAttribute("altered_pal");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("list") != null)
                {
                    ArrayList<Palestra> pals = null;
                    
                    if(request.getParameter("evt") != null)
                    {
                        try
                        {
                            codevt = Integer.parseInt(request.getParameter("evt"));
                            pals = pald.search(0, "eve_codigo", ""+codevt);
                        }
                        catch(NumberFormatException ex)
                        {
                            System.out.println("Erro ao converter");
                        }
                    }
                    else
                    {
                        pals = pald.list();
                    }
                    
//                    if(request.getParameter("search") != null)                    
//                    {
//                        search = request.getParameter("search");
//                        pals = pald.search("pal_nome", search);                                                
//                    }                                                            
                    
                    session.setAttribute("listaPal", pals);
                }
                                                                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    //response.sendRedirect(path);                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/palestra/"+path, title[0], ((Usuario)session.getAttribute("user")).getLogin()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                }
                else
                {
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/aluno/index.jsp");            
                    request.setAttribute("configuracao", new ConfigPagina("/admin/evento/perfil.jsp?codevt="+evt.getCodigo(), title[0], ((Usuario)session.getAttribute("user")).getLogin()));
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
