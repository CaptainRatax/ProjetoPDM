package com.example.projetopdmgrupo3.models;

public class InspecaoOnGoing {
    int id;
    String localidade;
    String morada;
    String codigoPostal;
    String entrada;
    int isInspecaoOnGoing;

    public InspecaoOnGoing() {
    }

    public InspecaoOnGoing(int id, String localidade, String morada, String codigoPostal, String entrada, int isInspecaoOnGoing) {
        this.id = id;
        this.localidade = localidade;
        this.morada = morada;
        this.codigoPostal = codigoPostal;
        this.entrada = entrada;
        this.isInspecaoOnGoing = isInspecaoOnGoing;
    }

    public InspecaoOnGoing(String localidade, String morada, String codigoPostal, String entrada, int isInspecaoOnGoing) {
        this.localidade = localidade;
        this.morada = morada;
        this.codigoPostal = codigoPostal;
        this.entrada = entrada;
        this.isInspecaoOnGoing = isInspecaoOnGoing;
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

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public int getIsInspecaoOnGoing() {
        return isInspecaoOnGoing;
    }

    public void setIsInspecaoOnGoing(int isInspecaoOnGoing) {
        this.isInspecaoOnGoing = isInspecaoOnGoing;
    }
}
