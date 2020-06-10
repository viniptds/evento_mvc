/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author viniciuspadovan
 */
public class Matricula implements Serializable{
    int codigo;
    Aluno aluno;
    Evento evento;
    boolean confirmado;    

    public Matricula(int codigo, Aluno aluno, Evento evento, boolean confirmado) {
        this.codigo = codigo;
        this.aluno = aluno;
        this.evento = evento;
        this.confirmado = confirmado;
    }

    public Matricula() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }
    
    
    
}
