/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Evento;
import model.Palestra;
import persist.Conexao;
import persist.DAOException;

/**
 *
 * @author rique
 */
public class EventoDAO 
{    
    public EventoDAO() {
        
    }
            
    public void insert(Evento evt)
    {
        String sql = "insert into evento (eve_nome, eve_inicio, eve_fim) values ('#1', '#2', '#3')";
        
        sql = sql.replace("#1", evt.getNome());
        sql = sql.replace("#2",""+ evt.getInicio());
        sql = sql.replace("#3",""+ evt.getFim());

        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex);            
        }
    }
    
    public void remove(Evento evt)
    {
        PalestraDAO pd = new PalestraDAO();
        
        Evento ev = busca(evt.getCodigo());
        for(Palestra p : ev.getPals())
            pd.remove(p);
        
        String sql = "delete from evento where eve_codigo = #1";
        
        sql = sql.replace("#1", ""+evt.getCodigo());
        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex);            
        }
    }
    
    public void update(Evento registro)
            throws DAOException {
        String sql = "update evento set eve_nome = '" + registro.getNome() + "', eve_inicio = '" + registro.getInicio()+"', eve_fim = '" + registro.getFim()+"'where eve_codigo = " + registro.getCodigo();
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erro alterando registro.");
        } catch (NullPointerException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
    }

    private Evento gerar(ResultSet rs) throws SQLException {
        PalestraDAO pd = new PalestraDAO();
        int codevt = rs.getInt("eve_codigo");
        return new Evento(codevt, rs.getString("eve_nome"), LocalDate.parse(rs.getDate("eve_inicio").toString()), 
                LocalDate.parse(rs.getDate("eve_fim").toString()), pd.search(0, "eve_codigo", ""+codevt));
    }

    public Evento busca(int codigo) {
        String sql = "select * from evento where eve_codigo = " + codigo;
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if (rs.next()) {
                        return gerar(rs);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return null;
    }
    
    public ArrayList<Evento> listar(boolean edisponivel, int ...param)
    {
        ArrayList<Evento> evt = new ArrayList<>();
        String sql = "select * from evento ";
        
        if(edisponivel)
        {
            sql = "select evento.eve_codigo, evento.eve_nome, evento.eve_inicio, evento.eve_fim from evento\n" +
            "inner join palestra on palestra.eve_codigo = evento.eve_codigo\n" +
            "left join matricula on matricula.eve_codigo = evento.eve_codigo\n" +
            "left join matricula_palestra on matricula_palestra.pal_codigo = palestra.pal_codigo\n";
            
            if(param.length > 0 )
                sql+= " where matricula.alu_codigo <> "+param[0] + " or matricula_palestra.mat_codigo is null";     
            
            sql+=" group by evento.eve_codigo "
                    + "having sum(palestra.pal_nr_inscritos_max) > count(matricula_palestra.mat_codigo)\n";
        }
        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        
                         evt.add(gerar(rs));
                    }
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return evt;
    }

    public ArrayList search(String termo, String field)
    {
        ArrayList<Evento> lus = new ArrayList();
        
        String sql = "select * from evento";
        if(termo.length() > 0)
        { 
            sql += " where #1 like '%#2%'"; // ... offset 1
                
            sql = sql.replace("#1", ""+field); 
            sql = sql.replace("#2", ""+termo);                
        }
        sql += " order by eve_nome";
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lus.add(gerar(rs));
                    }                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return lus;    
    }
                
}
