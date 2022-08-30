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
public class Carteira {
    private double saldo;
    
    Carteira(double saldo) {
        this.saldo = saldo;
    }
    
    double getSaldo(){
        return this.saldo;
    }
    
    void subtraiValor(double valor) {
        this.saldo -= valor;
    }
    
}
