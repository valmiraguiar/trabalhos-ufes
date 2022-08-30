/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jornaleiro.valmir.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aluno
 */
public class Jornaleiro {
    private List<Mensalidade> mensalidades;
    private double totalRecebido;
    private List<Cliente> clientes;
    
    public Jornaleiro(List<Cliente> clientes) {
        this.clientes = clientes;
        this.mensalidades = new ArrayList<>();
    }
    
    public Jornaleiro(List<Cliente> clientes, List<Mensalidade> mensalidades) {
        this.clientes = clientes;
        this.mensalidades = mensalidades;
    }
    
    public void setValorMensalidadeCliente(Cliente cliente, double valorMensalidade) {
        mensalidades.add(new Mensalidade(cliente, valorMensalidade));
    }
    
    public double getValorMensalidadeCliente(String nomeCliente) {
        for(Mensalidade mensalidade : this.mensalidades) {
            if(mensalidade.getCliente().getNome().equals(nomeCliente))
                return mensalidade.valorMensalidade();
        }
        return -1;
    }
    
    public void coletarPagamento() {       
        
        for(Cliente cliente : this.clientes) {
            double valorMensalidade = getValorMensalidadeCliente(cliente.getNome());
            if(valorMensalidade == -1){
                System.out.println("Cliente não possui valor de mensalidade");
                return;
            }
            if(cliente.realizarPagamento(valorMensalidade)) {
                this.totalRecebido += valorMensalidade;
            } else {
                System.out.println("Cliente sem dinheiro!");
            }
        }
    }
}
