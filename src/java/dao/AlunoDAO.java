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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Cidade;
import persist.Conexao;
import persist.DAOException;

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
    
    public void remove(Aluno al)
    {
        String sql = "delete from aluno where alu_cod = #1";
        
        sql = sql.replace("#1", ""+al.getCodigo());
        
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
    
    public void update(Aluno registro)
            throws DAOException {
        String sql = "update aluno set alu_nome = '" + registro.getNome() + "', alu_email = '" + registro.getEmail()+"', alu_senha = '" + registro.getSenha()+"', alu_endereco = '" + registro.getEndereco()+"', alu_complemento = '" + registro.getComplemento()+"" 
                + "', alu_cep = '" + registro.getCep()+"', alu_cpf = '" + registro.getCpf()+"', alu_numero = '" + registro.getNum()+"', cid_codigo = '" + registro.getCidade().getCodigo()+"', alu_data_nasc = '" + registro.getDatanasc()+"'where alu_codigo = " + registro.getCodigo();
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

    private Aluno gerar(ResultSet rs) throws SQLException {
        CidadeDAO cidDAO = new CidadeDAO();
        return new Aluno(rs.getInt("alu_codigo"), rs.getInt("alu_numero"), rs.getString("alu_nome"), 
                rs.getString("alu_email"), rs.getString("alu_senha"), rs.getString("alu_endereco"), 
                rs.getString("alu_complemento"), rs.getString("alu_cep"), rs.getString("alu_cpf"), 
                LocalDate.parse(rs.getDate("alu_data_nasc").toString()), cidDAO.busca(rs.getInt("cid_codigo")));
    }

    public Aluno busca(int codigo) {
        String sql = "select * from aluno where alu_codigo = " + codigo;
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
