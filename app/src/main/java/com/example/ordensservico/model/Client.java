package com.example.ordensservico.model;

import java.io.Serializable;

public class Client implements Serializable {

    private String nome, email, telefone;
    private int id;
    private boolean ativo;

    public Client(String nome, String email, String telefone, int id) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.id = id;
        this.ativo = true;
    }

    public Client(String nome, String email, String telefone, int id, boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.id = id;
        this.ativo = ativo;
    }

    public Client() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Dados do cliente:\n" +
                "Nome= " + nome + '\n' +
                "E-mail= " + email + '\n' +
                "Telefone= " + telefone;
    }
}
