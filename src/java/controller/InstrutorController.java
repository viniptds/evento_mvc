/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.InstrutorDAO;
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
import model.Instrutor;
import model.Palestra;
import model.Usuario;
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
            
            Palestra pal = (Palestra)session.getAttribute("altered_pal");
            PalestraDAO pd = new PalestraDAO();
            String path;    
            String title [] = {"Menu Instrutor", "Listagem de Instrutores", "Instrutor", "Vínculo de Instrutor"};
            
            String search, nome = "", curr = "";
            int cod = 0;
                                    
            session.removeAttribute("listaInst");
            
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
                if(request.getParameter("codinst") != null)
                {
                    try 
                    {
                        cod = Integer.parseInt(request.getParameter("codinst"));

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
                                if(pal != null)
                                {                                                                    
                                    in = new Instrutor(cod, nome, curr);                                                                
                                    if(session.getAttribute("altered_inst") != null)
                                    {                   
                                        in.setCodigo(((Instrutor)session.getAttribute("altered_inst")).getCodigo());
                                        insd.update(in);
                                        session.removeAttribute("altered_inst");
                                        System.out.println("Alterado!");
                                    }
                                    else
                                    {
                                        insd.insert(in);    
                                        
                                        pal.getInstruts().add(in);
                                        pd.addInstrutor(in, pal.getCod());
                                        
                                        System.out.println("Inserido!");
                                        
                                    }
                                    cod = 0;
                                    nome = curr = "";     
                                    request.removeAttribute("bChange");
                                }
                            }
                        }
                    }
                }            
                
                if(request.getParameter("delete") != null)
                {
                    in = (Instrutor)session.getAttribute("altered_inst");                    
                    if(request.getParameter("delete").equals("true") && in != null)
                    {                                                      
                        if(pal != null || pal.getInstruts().contains(in))
                        {
                            pal.getInstruts().remove(in);
                            pd.removeInstrutor(in, pal.getCod());
                        }
                        
                        if(request.getParameter("all") != null)
                            insd.remove(in);
                        
                        System.out.println("Removido!");                    
                        
                    }
                    session.removeAttribute("altered_inst");
                    //response.sendRedirect("ApplicationController");
                }
                
                if(request.getParameter("join") != null)
                {
                    System.out.println(pal.getCod() + "  -   "+ in.getNome());
                    pal.getInstruts().add(in);
                    pd.addInstrutor(in, pal.getCod());
                    session.removeAttribute("altered_inst");
                }
                
                if(request.getParameter("list") != null)
                {      
                    ArrayList<Instrutor> insts = null;
                    
                    if(pal != null)
                    {
                        insts = pal.getInstruts();
                    }    
                    else
                    {
                        insts = insd.listar();
                    }
                    
                    if(request.getParameter("filter") != null)
                    {
                        insts = insd.listar();                        
                        insts.removeAll(insd.search(""+pal.getCod(), "pal_codigo"));                                
                    }
                    
//                    if(request.getParameter("search") == null)
//                    {
//                        search = request.getParameter("search");
//                        session.setAttribute("listaInst", insd.search(search, "ins_nome"));                        
//                    }
                    
                    session.setAttribute("listaInst", insts);
                    
                }
                
                
                
                if(request.getParameter("path") != null)
                {
                    path = request.getParameter("path");
                    
                    request.setAttribute("configuracao", new ConfigPagina("/admin/instrutor/"+path, title[hd], ((Usuario)session.getAttribute("user")).getLogin()));
                    RequestDispatcher rd = request.getRequestDispatcher("_template.jsp");
                    rd.forward(request, response);
                    //response.sendRedirect(this.getServletContext().getContextPath()+"/admin/usuario/"+path);
                }                
                else
                {
                    session.removeAttribute("altered_inst");
                    request.setAttribute("configuracao", new ConfigPagina("/admin", title[hd], ((Usuario)session.getAttribute("user")).getLogin()));
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
