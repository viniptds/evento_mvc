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
    ArrayList<Matricula> mats;
    int capacidade, cod;
    String descricao, nome;    
    LocalDate data;

    public Palestra()
    {
        
    }
    
    public Palestra(int cod, String nome, String descricao, int capacidade, LocalDate data, ArrayList<Instrutor> instruts, ArrayList<Matricula> mats) {
        this.instruts = instruts;
        this.mats = mats;
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

    public ArrayList<Matricula> getMatriculas() {
        return mats;
    }

    public void setAlunos(ArrayList<Matricula> mats) {
        this.mats = mats;
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
    
}
