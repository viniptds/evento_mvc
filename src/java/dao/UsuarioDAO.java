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
import model.Usuario;
import persist.Conexao;

/**
 *
 * @author viniciuspadovan
 */
public class UsuarioDAO {
    
    public Usuario login(String login, String pass)
    {               
        String sql = "select * from usuario where usu_login = '#1' and usu_senha = '#2'";
        
        sql = sql.replace("#1", login);
        sql = sql.replace("#2", pass);
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    if (rs.next()) 
                    {
                        return new Usuario(rs.getInt("usu_codigo"), rs.getString("usu_nome"), 
                                rs.getString("usu_login"), rs.getString("usu_senha"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }                
        return null;
    }
    
    public Usuario get(int cod)
    {
        String sql = "select * from usuario where usu_codigo = #1";
        
        sql = sql.replace("#1", ""+cod);        
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    if (rs.next()) 
                    {
                        return new Usuario(rs.getInt("usu_codigo"), rs.getString("usu_nome"), 
                                rs.getString("usu_login"), rs.getString("usu_senha"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
                
        return null;
    }   
    
    public ArrayList list()
    {
        ArrayList<Usuario> lus = new ArrayList();
        
        String sql = "select * from usuario order by usu_nome"; // ... offset 1
         
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lus.add(new Usuario(rs.getInt("usu_codigo"), rs.getString("usu_nome"), 
                                rs.getString("usu_login"), rs.getString("usu_senha")));
                    }
                    return lus;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return null;    
    }
    
    public ArrayList search(String termo, String field)
    {
        ArrayList<Usuario> lus = new ArrayList();
        
        String sql = "select * from usuario";
        if(termo.length() > 0)
        { 
            sql += " where #1 like '%#2%'"; // ... offset 1
                
            sql = sql.replace("#1", ""+field); 
            sql = sql.replace("#2", ""+termo);                
        }
        sql += " order by usu_nome";
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lus.add(new Usuario(rs.getInt("usu_codigo"), rs.getString("usu_nome"), 
                                rs.getString("usu_login"), rs.getString("usu_senha")));
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
    
    public boolean update(Usuario u)
    {
        String sql = "update usuario set usu_nome = '#1', usu_login = '#2', usu_senha = '#3' where usu_codigo = #4";
        sql = sql.replace("#1", u.getNome());
        sql = sql.replace("#2", u.getLogin());
        sql = sql.replace("#3", u.getSenha());
        sql = sql.replace("#4", ""+u.getCodigo());
        
        try (Connection conn = Conexao.connect())
        {
            try (Statement st = conn.createStatement())
            {
                return st.execute(sql);
            }
        }
        catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return false;    
        
    }
        
    
    public boolean insert(Usuario u)
    {
        String sql = "insert into usuario (usu_nome, usu_login, usu_senha) values ('#1', '#2', '#3')";
        sql = sql.replace("#1", u.getNome());
        sql = sql.replace("#2", u.getLogin());
        sql = sql.replace("#3", u.getSenha());
        
        try (Connection conn = Conexao.connect())
        {
            try (Statement st = conn.createStatement())
            {
                return st.execute(sql);
            }
        }
        catch (SQLException ex) {
            System.out.println("Erro no SQL. "+ex.getMessage());
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return false;            
    }
    
    public boolean remove(Usuario u)
    {
        String sql = "delete from usuario where usu_codigo = #1";
        
        sql = sql.replace("#1", ""+u.getCodigo());
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {                
                return st.execute(sql);                
            }  
        }      
        catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        return false;
    }
    
}
