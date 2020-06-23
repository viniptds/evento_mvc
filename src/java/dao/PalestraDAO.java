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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Instrutor;
import model.Matricula;
import model.Palestra;
import persist.Conexao;

/**
 *
 * @author viniciuspadovan
 */
public class PalestraDAO {

    private AlunoDAO ald;
    private EventoDAO evd;
    private InstrutorDAO insd;    
    private MatriculaDAO matd;
    
    public PalestraDAO()
    {                   
        insd = new InstrutorDAO();
        matd = new MatriculaDAO();
    }
    
    public Palestra gerar(ResultSet rs, int op)
    {                
        Palestra p = null;
        try {
            
            int codevt = rs.getInt("eve_codigo"), palcod = rs.getInt("pal_codigo");
            
            p = new Palestra(palcod, rs.getString("pal_nome"),
                    rs.getString("pal_descricao"), rs.getInt("pal_nr_inscritos_max"), 
                    LocalDate.parse(rs.getDate("pal_data").toString()), 
                    insd.search(""+palcod, "pal_codigo"), matd.searchMatPal(palcod, op));            
        } catch (SQLException ex) {
            Logger.getLogger(PalestraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public Palestra get(int cod)
    {
        String sql = "select * from palestra where pal_codigo = #1";
        
        sql = sql.replace("#1", ""+cod);        
        
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    if (rs.next()) 
                    {
                        return gerar(rs, 0);
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
        ArrayList<Palestra> lus = new ArrayList();
        
        String sql = "select * from palestra order by pal_nome"; // ... offset 1
         
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lus.add(gerar(rs, 0));
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
    
    public ArrayList<Palestra> search(int op, String ...params)
    {
        
        ArrayList<Palestra> lus = new ArrayList();
        
        String sql = "select palestra.pal_codigo, pal_nome, eve_codigo, pal_descricao, pal_data, pal_nr_inscritos_max from palestra ";
        
        if(op > 0)
        {
            sql += " left join matricula_palestra on matricula_palestra.pal_codigo = palestra.pal_codigo";
        }
        if(params.length % 2 == 0)
        {
            sql+= " where ";
            for(int i = 0; i<params.length; i++)
            {
                if(params[i].equals("pal_codigo") || params[i].equals("eve_codigo"))
                {
                    sql+= params[i] +" = "+params[++i];
                }
                else
                {
                    sql+= params[i] +" = "+params[++i];
                }
                
                if(i+2 <= params.length)
                {
                    sql+=", ";
                }
            }
        }
                       
        if(op>0)
        {
            sql+= " group by palestra.pal_codigo having sum(palestra.pal_nr_inscritos_max) > count(matricula_palestra.mat_codigo)";
        }
        
        sql += " order by pal_nome ";
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    while (rs.next()) 
                    {
                        lus.add(gerar(rs, op));
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
    
    public boolean update(Palestra p)
    {
        String sql = "update palestra set pal_nome = '#1', pal_descricao = '#2', pal_nr_inscritos_max = #3, pal_data = '#4' where pal_codigo = #5";
        sql = sql.replace("#1", p.getNome());
        sql = sql.replace("#2", p.getDescricao());
        sql = sql.replace("#3", ""+p.getCapacidade());
        sql = sql.replace("#4", ""+p.getData());
        sql = sql.replace("#5", ""+p.getCod());
        
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
        
    
    public boolean insert(Palestra p)
    {
        String sql = "insert into palestra (pal_nome, pal_descricao, pal_nr_inscritos_max, eve_codigo, pal_data) values ('#1', '#2', #3, #4, '#5')";
        sql = sql.replace("#1", p.getNome());
        sql = sql.replace("#2", p.getDescricao());
        sql = sql.replace("#3", ""+p.getCapacidade());
        sql = sql.replace("#4", ""+p.getCodevt());
        sql = sql.replace("#5", ""+p.getData());
                
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
    
    public boolean remove(Palestra p)
    {
        String sql = "delete from palestra where pal_codigo = #1";
        
        sql = sql.replace("#1", ""+p.getCod());
        
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
    
    public boolean addInstrutor(Instrutor in, int codevt)
    {
        String sql = "insert into palestra_instrutor (ins_codigo, pal_codigo) values (#1, #2)";
        sql = sql.replace("#1", ""+in.getCodigo());
        sql = sql.replace("#2", ""+codevt);
                
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
    
    public boolean removeInstrutor(Instrutor ins, int pal)
    {
        String sql = "delete from palestra_instrutor where pal_codigo = #1 and ins_codigo = #2";
        sql = sql.replace("#1", ""+pal);
        sql = sql.replace("#2", ""+ins.getCodigo());
        
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
    
    public Palestra getByMatricula(int mat)
    {
        Palestra p = null;
        
        String sql = "select * from matricula_palestra where mat_codigo = "+mat; // ... offset 1
         
        try (Connection conn = Conexao.connect()) 
        {
            try (Statement st = conn.createStatement()) 
            {
                try (ResultSet rs = st.executeQuery(sql)) 
                {
                    if (rs.next()) 
                    {
                        p = get(rs.getInt("pal_codigo"));
                    }                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no SQL.");
        } catch (NullPointerException ex) {            
            System.out.println("Falha abrindo banco de dados.");
        }
        
        return p;    
    }
    
}
