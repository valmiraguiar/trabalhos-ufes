package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model;

public class ItemPedido {
    private int qtdPedido;
    private double valorUnitario;
    private double valorTotal;
    private Produto produto;

    public ItemPedido(int qtdPedido, Produto produto) {
        this.qtdPedido = qtdPedido;
        this.produto = produto;
        this.valorUnitario = produto.getPrecoUnitario();
        this.valorTotal = this.valorUnitario * qtdPedido;
    }

    public int getQtdPedido() {
        return qtdPedido;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "qtdPedido=" + qtdPedido +
                ", valorUnitario=" + valorUnitario +
                ", valorTotal=" + valorTotal +
                ", produto=" + produto.toString() +
                '}';
    }
}
