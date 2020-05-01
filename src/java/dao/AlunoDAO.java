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
import model.Aluno;
import persist.Conexao;

/**
 *
 * @author viniciuspadovan
 */
public class AlunoDAO {
    
    public Aluno login(String login, String pass)
    {              
        Aluno al = null;
        String sql = "select * from aluno where alu_email = '#1' and alu_senha = '#2'";
        
        sql = sql.replace("#1", login);
        sql = sql.replace("#2", pass);
        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    
                    if (rs.next()) {
                        
                        al = new Aluno();
                        
                        al.setCodigo(rs.getInt("alu_codigo"));
                        al.setNome(rs.getString("alu_nome"));
                        al.setSenha(rs.getString("alu_senha"));
                        al.setEmail(rs.getString("alu_email"));                       
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        
        return al;
    }
    
    public void insert(Aluno al)
    {
        String sql = "insert into aluno (alu_nome, alu_email, alu_senha, "
                + "alu_endereco, alu_numero, alu_complemento, alu_cep, "
                + "cid_codigo, alu_data_nasc, alu_cpf) values (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10)";
        
        sql = sql.replace("#1", al.getNome());
        sql = sql.replace("#2", al.getEmail());
        sql = sql.replace("#3", al.getSenha());
        sql = sql.replace("#4", al.getEndereco());
        sql = sql.replace("#5", ""+al.getNum());
        sql = sql.replace("#6", al.getComplemento());
        sql = sql.replace("#7", al.getCep());
        sql = sql.replace("#8", ""+al.getCidade().getCodigo());
        sql = sql.replace("#9", al.getDatanasc().toString());
        sql = sql.replace("#10", al.getCpf());
        
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

    
}
