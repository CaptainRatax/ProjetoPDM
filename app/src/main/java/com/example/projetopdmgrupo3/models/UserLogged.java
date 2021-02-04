package com.example.projetopdmgrupo3.models;

public class UserLogged {
    int id;
    int idInspecionador;
    String email;
    String telefone;
    String nome;
    String dataNascimento;
    String imagem;
    int isLoggedIn;

    //Usado para instancia
    public UserLogged(){

    }

    //Usado para Update


    public UserLogged(int id, int idInspecionador, String email, String telefone, String nome, String dataNascimento, String imagem, int isLoggedIn) {
        this.id = id;
        this.idInspecionador = idInspecionador;
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.imagem = imagem;
        this.isLoggedIn = isLoggedIn;
    }

    //Usado para Insert

    public UserLogged(int idInspecionador, String email, String telefone, String nome, String dataNascimento, String imagem, int isLoggedIn) {
        this.idInspecionador = idInspecionador;
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.imagem = imagem;
        this.isLoggedIn = isLoggedIn;
    }


    //-------------------------------GETTERS AND SETTERS--------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInspecionador() {
        return idInspecionador;
    }

    public void setIdInspecionador(int idInspecionador) {
        this.idInspecionador = idInspecionador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(int isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
