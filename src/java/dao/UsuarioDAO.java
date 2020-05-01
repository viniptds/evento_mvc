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
    
    
}
