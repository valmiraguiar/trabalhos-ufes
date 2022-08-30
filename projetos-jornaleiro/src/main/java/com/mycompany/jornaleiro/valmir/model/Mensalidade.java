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
public class Mensalidade {
    private Cliente cliente;
    private int mes;
    private double valorMensalidade;

    public Mensalidade(Jornaleiro jornaleiro, Cliente cliente, int mes, double valorMensalidade) {
        this.jornaleiro = jornaleiro;
        this.cliente = cliente;
        this.mes = mes;
        this.valorMensalidade = valorMensalidade;
    }

    public Jornaleiro getJornaleiro() {
        return jornaleiro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getMes() {
        return mes;
    }

    public double getValorMensalidade() {
        return valorMensalidade;
    }
    
}
