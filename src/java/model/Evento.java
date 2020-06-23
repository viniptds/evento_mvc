/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author rique
 */
public class Evento implements Serializable
{
    int codigo;
    String nome;
    LocalDate inicio, fim;
    ArrayList<Palestra> pals;

    public Evento()
    {
        
    }
    
    public Evento(int codigo, String nome, LocalDate inicio, LocalDate fim, ArrayList<Palestra> pals) {
        this.codigo = codigo;
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.pals = pals;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public ArrayList<Palestra> getPals() {
        return pals;
    }

    public void setPals(ArrayList<Palestra> pals) {
        this.pals = pals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evento{" + "codigo=" + codigo + ", nome=" + nome + ", inicio=" + inicio + ", fim=" + fim + ", pals=" + pals + '}';
    }
                
}
