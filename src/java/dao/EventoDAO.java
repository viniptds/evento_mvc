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
import model.Evento;
import persist.Conexao;
import persist.DAOException;

/**
 *
 * @author rique
 */
public class EventoDAO 
{
    public void insert(Evento evt)
    {
        String sql = "insert into evento (eve_nome, eve_inicio, eve_fim) values (#1, #2, #3)";
        
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
        return new Evento(rs.getInt("eve_codigo"), rs.getString("eve_nome"), LocalDate.parse(rs.getDate("eve_inicio").toString()), 
                LocalDate.parse(rs.getDate("eve_fim").toString()));
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
    
}
