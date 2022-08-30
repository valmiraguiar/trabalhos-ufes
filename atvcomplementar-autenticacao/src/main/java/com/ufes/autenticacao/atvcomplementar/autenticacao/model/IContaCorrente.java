package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

public interface IContaCorrente {
    
    public void depositar(double valor);
    public void sacar(double valor);
    public void pagar(double valor);
    public void transferir(double valor, IContaCorrente contaDestino);
    public void ativar();
    public void desativar();
    public String getNumero();
    public double getSaldo();
    public boolean isAtiva();
    public Usuario getUsuario();
}
