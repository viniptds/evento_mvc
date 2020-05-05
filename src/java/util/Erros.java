/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rique
 */
public class Erros {
    private final List<String> mensagens;

    public Erros() {
        mensagens = new ArrayList<>();
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void addMensagem(String msg) {
        mensagens.add(msg);
    }

    public boolean isPossuiMensagem() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return mensagens.isEmpty();
    }
}
