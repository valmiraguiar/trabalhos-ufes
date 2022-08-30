/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jornaleiro.valmir.model;

/**
 *
 * @author Aluno
 */
public class Cliente {
    private String nome;
    private Carteira carteira;
    
    public Cliente(String nome, double saldo) {
        this.nome = nome;
        this.carteira = new Carteira(saldo);
    }
    
    public boolean realizarPagamento(double valorCobranca) {
        if(valorCobranca <= carteira.getSaldo()) {
            this.carteira.subtraiValor(valorCobranca);
            return true;
        } 
        return false;
    }

    public String getNome() {
        return nome;
    }
}
