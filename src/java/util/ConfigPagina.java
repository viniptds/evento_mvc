package util;

import java.io.Serializable;

/**
 * Bean para as configurações de uma página
 *
 * @author Caetano
 */
public class ConfigPagina implements Serializable {

    private String includeURL;
    private String titulo;    

    public ConfigPagina() {
        this(null, null);
    }

    public ConfigPagina(String includeURL, String titulo) {
        this.includeURL = includeURL;
        this.titulo = titulo;
    }

    public String getIncludeURL() {
        return includeURL;
    }

    public String getTitulo() {
        return titulo;
    }

}
