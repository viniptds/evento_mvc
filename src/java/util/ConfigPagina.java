package util;

import java.io.Serializable;
import model.Usuario;

/**
 * Bean para as configurações de uma página
 *
 * @author Caetano
 */
public class ConfigPagina implements Serializable {

    private String includeURL;
    private String titulo;        
    private String log;
    
    
    public ConfigPagina() {
        this(null, null, null);
    }

    public ConfigPagina(String includeURL, String titulo, String us) {
        this.includeURL = includeURL;
        this.titulo = titulo;
        this.log = us;
    }

    public String getIncludeURL() {
        return includeURL;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String us) {
        this.log = us;
    }
    

}
