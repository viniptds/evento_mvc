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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Instrutor;
import persist.Conexao;
import persist.DAOException;

/**
 *
 * @author rique
 */
public class InstrutorDAO 
{
    private Instrutor gerar(ResultSet rs) throws SQLException {
        return new Instrutor(rs.getInt("ins_codigo"), rs.getString("ins_nome"), 
                rs.getString("ins_curriculo"));
    }
    
    public void insert(Instrutor ins)
    {
        String sql = "insert into instrutor (ins_nome, ins_curriculo) values ('#1', '#2')";
        
        sql = sql.replace("#1", ins.getNome());
        sql = sql.replace("#2", ins.getCurriculo());

        
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
    
    public void remove(Instrutor ins)
    {
        String sql = "delete from instrutor where ins_codigo = #1";
        
        sql = sql.replace("#1", ""+ins.getCodigo());
        
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
    
    public void update(Instrutor registro)
            throws DAOException {
        String sql = "update instrutor set ins_nome = '" + registro.getNome() + "', ins_curriculo = '" + registro.getCurriculo()+"' where ins_codigo = " + registro.getCodigo();
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

    public ArrayList<Instrutor> listar()
    {
        ArrayList<Instrutor> in = new ArrayList<>();
        String sql = "select * from instrutor order by ins_nome";
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                         in.add(gerar(rs));
                    }
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return in;
    }

    public ArrayList search(String termo, String field)
    {
        ArrayList<Instrutor> lus = new ArrayList();
        
        String sql = "select * from instrutor";
        if(termo.length() > 0)
        { 
            sql += " where #1 like '%#2%'"; // ... offset 1
                
            sql = sql.replace("#1", ""+field); 
            sql = sql.replace("#2", ""+termo);                
        }
        sql += " order by ins_nome";
        
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
    
    public Instrutor busca(int codigo) {
        String sql = "select * from instrutor where ins_codigo = " + codigo;
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
    
    
}
