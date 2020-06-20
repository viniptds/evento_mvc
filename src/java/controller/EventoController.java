/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EventoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Evento;
import model.Usuario;
import util.ConfigPagina;
import util.Erros;

/**
 *
 * @author viniciuspadovan
 */
public class EventoController extends HttpServlet {

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
            Erros err = new Erros();
            
            HttpSession session = request.getSession();
            
            EventoDAO evtd = new EventoDAO();            
            Evento evt = null;
            
            String path, data[];    
            String title [] = {"Menu Evento", "Listagem de Eventos", "Evento"};
            
            String search, nome = "";
            LocalDate inicio, fim;
            int cod = 0, dia, mes, ano;
                                    
            session.removeAttribute("listaEvento");            
            
            if(session.getAttribute("user") == null)
            {
                response.sendRedirect("ApplicationController");
            }
            else
            {
                if(request.getParameter("codevt") != null)
                {
                    try 
                    {
                        cod = Integer.parseInt(request.getParameter("codevt"));

                        if(cod > 0)
                        {
                            evt = evtd.busca(cod);
                            session.setAttribute("altered_evento", evt);
                        }
                        else
                            response.sendRedirect("listagem.jsp");
                    }
                    catch(NumberFormatException ex)
                    {
                        err.addMensagem("Erro ao converter valor!");
                    }
                    request.removeAttribute("codevt");
                }
                else
                {
                    session.removeAttribute("altered_evento");
                }
                
                if(request.getParameter("bChange") != null)
                {
                    if(request.getParameter("nome") != null)
                    {
                        nome = request.getParameter("nome");

                        if(request.getParameter("inicio") != null)
                        {
                            
                            data = request.getParameter("inicio").split("/");
                            
                            try
                            {
                                dia = Integer.parseInt(data[0]);
                                mes = Integer.parseInt(data[1]);
                                ano = Integer.parseInt(data[2]);

                                inicio = LocalDate.of(ano, mes, dia);
                                
                                if(request.getParameter("fim") != null)
                                {
                                    data = request.getParameter("fim").split("/");
                                    
                                    try
                                    {
                                        dia = Integer.parseInt(data[0]);
                                        mes = Integer.parseInt(data[1]);
                                        ano = Integer.parseInt(data[2]);

                                        fim = LocalDate.of(ano, mes, dia);
                                        
                                        if(nome.length() > 0 && inicio!=null && fim!=null)
                                        {
                                            evt = new Evento(cod, nome, inicio, fim, null);
                                            if(session.getAttribute("altered_evento") != null)
                                            {                
                                                evt.setCodigo(((Evento)session.getAttribute("altered_evento")).getCodigo());
                                                evtd.update(evt);
                                                session.removeAttribute("altered_evento");
                                                System.out.println("Alterado!");
                                            }
                                            else
                                            {
                                                evtd.insert(evt);          
                                                System.out.println("Inserido!");
                                            }
                                            cod = 0;
                                            nome = "";
                                            inicio=null;
                                            fim=null;
                                            request.setAttribute("bChange", null);
                                        }
                                    }
                                    catch(NumberFormatException e)
                                    {
                                        err.addMensagem("<script> alert('Data Fim Inválida') </script>");
                                    }
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                err.addMensagem("<script> alert('Data Inicio Inválida') </script>");
                            }
                        }
                    }
                }            
                
                if(request.getParameter("delete") != null)
                {
                    evt = (Evento)session.getAttribute("altered_evento");
                    if(request.getParameter("delete").equals("true") && evt != null)
                    {                                                                        
                            evtd.remove(evt);
                            System.out.println("Removido!");                    
                        
                    }
                    session.removeAttribute("altered_evento");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("list") != null)
                {
                    if(request.getParameter("search") == null)
                    {
                        session.setAttribute("listaEvento", evtd.listar(false));
                    }
                    else
                    {
                        search = request.getParameter("search");
                        session.setAttribute("listaEvento", evtd.search(search, "eve_nome"));
                    }
                }
                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/evento/"+path, title[0], ((Usuario)session.getAttribute("user")).getLogin()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/usuario/"+path);
                }                
                else
                {
                    request.setAttribute("configuracao", new ConfigPagina("/admin/evento/index.jsp", title[0], ((Usuario)session.getAttribute("user")).getLogin()));
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
