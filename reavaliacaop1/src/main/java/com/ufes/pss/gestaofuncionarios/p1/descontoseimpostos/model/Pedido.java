package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {
    private String nomeCliente;
    private LocalDate data;
    private double valorTotal;
    private double valorDescontosEImpostos;
    private ArrayList<Imposto> impostosAplicados;
    private ArrayList<Desconto> descontosConcedidos;
    private ArrayList<ItemPedido> itens;

    public Pedido(String nome, LocalDate data) {
        this.nomeCliente = nome;
        this.data = data;
        this.valorTotal = 0.0;
        this.valorDescontosEImpostos = 0.0;
        this.impostosAplicados = new ArrayList<>();
        this.descontosConcedidos = new ArrayList<>();
        this.itens = new ArrayList<>();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public ArrayList<Imposto> getImpostosAplicados() {
        return impostosAplicados;
    }

    public ArrayList<Desconto> getDescontosConcedidos() {
        return descontosConcedidos;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public void addDesconto(Desconto desconto) {
        this.descontosConcedidos.add(desconto);
        this.valorDescontosEImpostos -= desconto.getValor();
    }

    public void addImposto(Imposto imposto) {
        this.impostosAplicados.add(imposto);
        this.valorDescontosEImpostos += imposto.getValor();
        System.out.println("->>>> "+this.valorDescontosEImpostos);
    }

    public void addItem(ItemPedido itemPedido) {
        itens.add(itemPedido);
        this.valorTotal += itemPedido.getValorTotal();
    }

    public double getValorFinalComDescontos() {
        return this.valorDescontosEImpostos;
    }

    @Override
    public String toString() {
        return "Pedido: \n" +
                "cliente = " + nomeCliente +
                "\ndata = " + data +
                "\nvalorTotal = " + valorTotal +
                "\nvalorFinalComDescontos = " + (valorTotal - valorDescontosEImpostos) +
                "\nimpostosAplicados = " + impostosAplicados.toString() +
                "\ndescontosConcedidos = " + descontosConcedidos.toString() +
                "\nitens = " + itens.toString();
    }
}
