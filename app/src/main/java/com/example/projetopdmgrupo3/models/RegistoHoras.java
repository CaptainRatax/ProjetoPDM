package com.example.projetopdmgrupo3.models;

public class RegistoHoras {
    int id;
    String entrada;
    String saida;
    int idObra;
    int idInspecionador;
    float nota;
    String observacoes;

    public RegistoHoras() {
    }

    public RegistoHoras(String entrada, String saida, int idObra, int idInspecionador, float nota, String observacoes) {
        this.entrada = entrada;
        this.saida = saida;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
        this.nota = nota;
        this.observacoes = observacoes;
    }

    public RegistoHoras(int id, String entrada, String saida, int idObra, int idInspecionador, float nota, String observacoes) {
        this.id = id;
        this.entrada = entrada;
        this.saida = saida;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
        this.nota = nota;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public int getIdObra() {
        return idObra;
    }

    public void setIdObra(int idObra) {
        this.idObra = idObra;
    }

    public int getIdInspecionador() {
        return idInspecionador;
    }

    public void setIdInspecionador(int idInspecionador) {
        this.idInspecionador = idInspecionador;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
