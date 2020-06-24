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
import model.Aluno;
import model.Evento;
import model.Matricula;
import model.Palestra;
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
        return new Matricula(rs.getInt("mat_codigo"), ald.busca(rs.getInt("alu_codigo")),
                rs.getBoolean("mat_confirmada"), evd.busca(rs.getInt("eve_codigo")));
    }
    
    public ArrayList<Matricula> getAll(int aluno) {
        ArrayList<Matricula> mats = new ArrayList();
        
        String sql = "select * from matricula where alu_codigo = #1 order by mat_confirmada";        
        sql = sql.replace("#1", ""+aluno);
        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                         mats.add(gerar(rs));
                    }                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Falha abrindo banco de dados.");
        }
        return mats;
    }
    
    public Matricula get(int codigo)
    {
        String sql = "select * from matricula where mat_codigo = #1";        
        sql = sql.replace("#1", ""+codigo);
        
        try (Connection conn = Conexao.connect()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if(rs.next()) {
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

    public boolean remove(Matricula m) {
        
        removeMatricula(m);
        
        String sql = "delete from matricula where mat_codigo = #1";

        sql = sql.replace("#1", ""+m.getCodigo());        
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

    public ArrayList<Matricula> search(String ...params) 
    {
        ArrayList<Matricula> lm = new ArrayList();
        
        String sql = "select mat_codigo, alu_codigo, mat_confirmada, eve_codigo from matricula";
        
        if(params.length % 2 == 0)
        {
            if(params[0].equals("pal_codigo"))
            {
                sql+= " left join matricula_palestra on matricula_palestra.mat_codigo = matricula.mat_codigo";
            }
            sql += " where ";
            for(int i = 0; i<params.length; i++)
            {                
                sql+= params[i] +" = "+params[++i];               
                
                if(i+2 <= params.length)
                {
                    sql+=", ";
                }
            }
        }
        
        sql += " order by mat_codigo";
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lm.add(gerar(rs));
                    }                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL. "+ex);
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return lm;    
    }
    
    public ArrayList<Aluno> searchMatPal(int cod, int confirmed)
    {
        ArrayList<Aluno> lus = new ArrayList();
        
        String sql = "select * from matricula inner join matricula_palestra "
                + "on matricula_palestra.mat_codigo = matricula.mat_codigo " +
"where matricula_palestra.pal_codigo = "+cod;                         
                
        if(confirmed == 1)
        {
            sql+= " and mat_confirmada = true";
        }
        else
            if(confirmed == 2)
            sql+= " and mat_confirmada = false";
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {                    
                        while (rs.next()) 
                        {
                            
                            lus.add(ald.busca(rs.getInt("alu_codigo")));
                        }                             
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL. "+ex);
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return lus;    
    }

    private Matricula gerarMats(ResultSet rs) {
        try {
            return get(rs.getInt("mat_codigo"));
        } catch (SQLException ex) {
            System.out.println("Erro  SQL "+ ex.getMessage());
            return null;
        }
    }
    
    public boolean changeStatus(int alu, boolean status)
    {
        String sql = "update matricula set mat_confirmada  = #1 where alu_codigo = #2";
        sql = sql.replace("#1", (status) ? "true": "false");
        sql = sql.replace("#2", ""+alu);
        
        
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
    
    public boolean addMatricula(Matricula mat, int pal)
    {
        String sql = "insert into matricula_palestra (mat_codigo, pal_codigo) values (#1, #2)";
        sql = sql.replace("#1", ""+mat.getCodigo());
        sql = sql.replace("#2", ""+pal);
        
        System.out.println(sql);
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
    public boolean removeMatricula(Matricula mat)
    {
        boolean res = false;
        String sql = "delete from matricula_palestra where mat_codigo = #1";
        
        sql = sql.replace("#1", ""+mat.getCodigo());
                
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {                
                res = st.execute(sql);                
            }  
        }      
        catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        return res;
    }

    public boolean insert(Matricula m) {
        boolean res = false;
        String sql = "insert into matricula (alu_codigo, eve_codigo, mat_confirmada) values (#1, #2, #3)";
        
        sql = sql.replace("#1", ""+m.getAluno().getCodigo());
        sql = sql.replace("#2", ""+m.getEvento().getCodigo());
        sql = sql.replace("#3", "false");        
                
        try (Connection conn = Conexao.connect())
        {
            try (Statement st = conn.createStatement())
            {
                res = st.execute(sql);
                m.setCodigo(new Conexao().getMaxPK("matricula", "mat_codigo"));
            }
        }
        catch (SQLException ex) {
            System.out.println("Erro no SQL. "+ex.getMessage());
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return res;            
    }

    public void update(int codigo, Evento evt, Palestra pal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
