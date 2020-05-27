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
    private Usuario us;
    
    
    public ConfigPagina() {
        this(null, null, null);
    }

    public ConfigPagina(String includeURL, String titulo, Usuario us) {
        this.includeURL = includeURL;
        this.titulo = titulo;
        this.us = us;
    }

    public String getIncludeURL() {
        return includeURL;
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }
    

}
