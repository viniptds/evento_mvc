package persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de Conexão
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/lp3_proj_evento";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "040318";

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(Conexao.URL, Conexao.USUARIO, Conexao.SENHA);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
