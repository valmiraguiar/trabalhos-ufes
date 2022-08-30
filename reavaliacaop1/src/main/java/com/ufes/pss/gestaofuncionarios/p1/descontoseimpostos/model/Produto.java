package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model;

public class Produto {
    private String nome;
    private double precoUnitario;
    private int qtdEstoque;
    private String categoria;

    public Produto(String nome, double precoUnitario, int qtdEstoque, String categoria) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
