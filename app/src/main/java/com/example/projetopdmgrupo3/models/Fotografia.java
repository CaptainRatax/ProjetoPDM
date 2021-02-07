package com.example.projetopdmgrupo3.models;

public class Fotografia {
    int id;
    int idCaso;
    String fotografia;

    public Fotografia() {
    }

    public Fotografia(int id, int idCaso, String fotografia) {
        this.id = id;
        this.idCaso = idCaso;
        this.fotografia = fotografia;
    }

    public Fotografia(int idCaso, String fotografia) {
        this.idCaso = idCaso;
        this.fotografia = fotografia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }
}
