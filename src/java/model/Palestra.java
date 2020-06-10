/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author viniciuspadovan
 */
public class Palestra {
    ArrayList<Instrutor> instruts;
    ArrayList<Aluno> alunos;
    int capacidade, cod;
    String descricao, nome;
    Evento evento;
    LocalDate data;

    public Palestra()
    {
        
    }
    
    public Palestra(int cod, String nome, String descricao, int capacidade, LocalDate data, ArrayList<Instrutor> instruts, ArrayList<Aluno> alunos, Evento evento) {
        this.instruts = instruts;
        this.alunos = alunos;
        this.capacidade = capacidade;
        this.cod = cod;
        this.descricao = descricao;
        this.nome = nome;
        this.evento = evento;
        this.data = data;
    }

    public ArrayList<Instrutor> getInstruts() {
        return instruts;
    }

    public void setInstruts(ArrayList<Instrutor> instruts) {
        this.instruts = instruts;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }        
    
}
