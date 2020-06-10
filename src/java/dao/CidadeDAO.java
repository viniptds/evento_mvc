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
import model.Cidade;
import persist.Conexao;
import persist.DAOException;
import util.Util;

/**
 *
 * @author rique
 */
public class CidadeDAO 
{
    private Cidade gerar(ResultSet rs) throws SQLException {
        UFDAO ufdao = new UFDAO();
        return new Cidade(rs.getInt("cid_codigo"), rs.getString("cid_nome"), ufdao.busca(rs.getInt("uf_codigo")));
    }

    public Cidade busca(int codigo) {
        String sql = "select cid_codigo, cid_nome, uf_codigo from cidade where cid_codigo = " + codigo;
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

    public ArrayList<Cidade> listar(String nome, String field) {
        String sql = "select * from cidade";
        if (Util.isNotEmpty(nome) && Util.isNotEmpty(field)) {
            if(field.equals("uf_codigo") || field.equals("cid_codigo"))
            {
                sql += " where "+field+" = " + nome;
            }
            else
                sql += " where "+field+" like '" + nome + "%'";
        }
        sql += " order by cid_nome";
        ArrayList<Cidade> resp = new ArrayList<>();
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        resp.add(gerar(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return resp;
    }

    public void inserir(Cidade novo)
            throws DAOException {
        String sql = "insert into cidade (cid_codigo, cid_nome, uf_codigo) values (" + novo.getCodigo() + ", '" + novo.getNome() + "', '" + novo.getUf().getCodigo() + "')";
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erro inserindo registro.");
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
    }

    public void alterar(Cidade registro)
            throws DAOException {
        String sql = "update cidade set cid_nome = '" + registro.getNome() + "', uf_codigo = '" + registro.getUf().getCodigo() + "' where cid_codigo = " + registro.getCodigo();
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erro alterando registro.");
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
    }

    public void excluir(Cidade registro)
            throws DAOException {
        excluir(registro.getCodigo());
    }

    public void excluir(int codigo)
            throws DAOException {
        String sql = "delete from cidade where cid_codigo = " + codigo;
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erro excluindo registro.");
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
    }
    
    
}
