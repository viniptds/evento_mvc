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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UF;
import persist.Conexao;
import persist.DAOException;
import util.Util;

/**
 *
 * @author rique
 */
public class UFDAO 
{
    private UF gerar(ResultSet rs) throws SQLException {
        return new UF(rs.getInt("uf_codigo"), rs.getString("uf_nome"), rs.getString("uf_sigla"));
    }

    public UF busca(int codigo) {
        String sql = "select uf_codigo, uf_nome, uf_sigla from uf where uf_codigo = " + codigo;
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if (rs.next()) {
                        return gerar(rs);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(UFDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return null;
    }
    
}
