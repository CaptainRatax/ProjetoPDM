package com.example.projetopdmgrupo3.models;

public class RegistoHoras {
    int id;
    String entrada;
    String saida;
    int idObra;
    int idInspecionador;

    public RegistoHoras() {
    }

    public RegistoHoras(String entrada, String saida, int idObra, int idInspecionador) {
        this.entrada = entrada;
        this.saida = saida;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
    }

    public RegistoHoras(int id, String entrada, String saida, int idObra, int idInspecionador) {
        this.id = id;
        this.entrada = entrada;
        this.saida = saida;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
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
}
