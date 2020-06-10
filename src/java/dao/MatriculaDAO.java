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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Matricula;
import persist.Conexao;
import persist.DAOException;

/**
 *
 * @author viniciuspadovan
 */
public class MatriculaDAO {
    EventoDAO evd;
    AlunoDAO ald;

    public MatriculaDAO() {
        evd = new EventoDAO();
        ald = new AlunoDAO();
    }            
    
    private Matricula gerar(ResultSet rs) throws SQLException {
        return new Matricula(rs.getInt("mat_codigo"), ald.busca(rs.getInt("alu_codigo")), evd.busca(rs.getInt("eve_codigo")), 
                rs.getBoolean("mat_confirmada"));
    }
    
    public Matricula busca(int codigo) {
        String sql = "select * from matricula where mat_codigo = " + codigo;
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
