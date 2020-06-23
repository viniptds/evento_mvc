package persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de Conexão
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/lp3_proj_evento";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "root";

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(Conexao.URL, Conexao.USUARIO, Conexao.SENHA);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private ResultSet consultar(String sql)
    {   ResultSet rs=null;
        try 
        {
           Statement statement = Conexao.connect().createStatement();
             //ResultSet.TYPE_SCROLL_INSENSITIVE,
             //ResultSet.CONCUR_READ_ONLY);
           rs = statement.executeQuery( sql );
           //statement.close();
        }
        catch ( SQLException sqlex )
        { 
            System.out.println("Erro: "+sqlex.toString());
            rs = null;
        }
        return rs;
    }
    
    
    public int getMaxPK(String tabela,String chave) 
    {
        String sql="select max("+chave+") from "+tabela;
        int max=0;
        ResultSet rs = consultar(sql);
        try 
        {
            if(rs.next())
                max=rs.getInt(1);
        }
        catch (SQLException sqlex)
        { 
             System.out.println("Erro: " + sqlex.toString());
             max = -1;
        }
        return max;
    }
}
