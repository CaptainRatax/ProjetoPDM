package com.example.testlogin;

import java.io.Serializable;

public class User implements Serializable {

    private String Id;
    private String Password;
    private String Email;
    private String Telefone;
    private String Nome;
    private String DataNascimento;
    private String CreatedAt;

    public User(String Id, String Password, String Email, String Telefone, String Nome, String DataNascimento, String CreatedAt) {
        this.setId(Id);
        this.setPassword(Password);
        this.setEmail(Email);
        this.setTelefone(Telefone);
        this.setNome(Nome);
        this.setDataNascimento(DataNascimento);
        this.setCreatedAt(CreatedAt);
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.CreatedAt = createdAt;
    }
}
