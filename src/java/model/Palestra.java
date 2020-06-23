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
 * @author viniciuspadovan
 */
public class Palestra implements Serializable {
    ArrayList<Instrutor> instruts;
    ArrayList<Aluno> alunos;
    int capacidade, cod;
    String descricao, nome;    
    LocalDate data;
    int codevt;
    
    public Palestra()
    {
        
    }
    
    public Palestra(int cod, String nome, String descricao, int capacidade, LocalDate data, ArrayList<Instrutor> instruts, ArrayList<Aluno> alunos) {
        this.instruts = instruts;
        this.alunos = alunos;
        this.capacidade = capacidade;
        this.cod = cod;
        this.descricao = descricao;
        this.nome = nome;        
        this.data = data;
    }

    public ArrayList<Instrutor> getInstruts() {
        return instruts;
    }

    public void setInstruts(ArrayList<Instrutor> instruts) {
        this.instruts = instruts;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }        

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    
    public int getCodevt() {
        return codevt;
    }

    public void setCodevt(int codevt) {
        this.codevt = codevt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Palestra other = (Palestra) obj;
        if (this.cod != other.cod) {
            return false;
        }
        return true;
    }       

    @Override
    public String toString() {
        return "Palestra{" + "instruts=" + instruts + ", alunos=" + alunos + ", capacidade=" + capacidade + ", cod=" + cod + ", descricao=" + descricao + ", nome=" + nome + ", data=" + data + ", codevt=" + codevt + '}';
    }
   
}
