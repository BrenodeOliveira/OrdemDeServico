package com.example.ordensservico.model;

import java.io.Serializable;

public class Card implements Serializable {

    private String preco, descricao, abertura, fechamento, status;
    private int id;

    public Card(String preco, String descricao, String abertura, String fechamento, String status, int id) {
        this.preco = preco;
        this.descricao = descricao;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.status = status;
        this.id = id;
    }

    public Card() {

    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
