package com.example.projetopdmgrupo3.models;

public class Obra {
    int id;
    String localidade;
    String morada;
    String codigoPostal;
    int idCliente;
    String createdAt;

    public Obra() {
    }

    public Obra(int id, String localidade, String morada, String codigoPostal, int idCliente, String createdAt) {
        this.id = id;
        this.localidade = localidade;
        this.morada = morada;
        this.codigoPostal = codigoPostal;
        this.idCliente = idCliente;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
