package com.example.projetopdmgrupo3.models;

public class Caso {
    int id;
    String titulo;
    String descricao;
    int idObra;
    int idInspecionador;
    String createdAt;
    int isSynced;

    public Caso() {
    }

    public Caso(int id, String titulo, String descricao, int idObra, int idInspecionador, String createdAt, int isSynced) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
        this.createdAt = createdAt;
        this.isSynced = isSynced;
    }

    public Caso(String titulo, String descricao, int idObra, int idInspecionador, String createdAt, int isSynced) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.idObra = idObra;
        this.idInspecionador = idInspecionador;
        this.createdAt = createdAt;
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}
