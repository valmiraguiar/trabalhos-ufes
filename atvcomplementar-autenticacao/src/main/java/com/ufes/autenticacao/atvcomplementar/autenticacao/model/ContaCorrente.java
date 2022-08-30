package com.ufes.autenticacao.atvcomplementar.autenticacao.model;


class ContaCorrente implements IContaCorrente{
    private final String numero;
    private double saldo;
    private boolean ativa;
    private final Usuario usuario;

    protected ContaCorrente(String numero, double saldoInicial, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.usuario = usuario;
    }
    
    @Override
    public void depositar(double valor) {
        this.saldo += valor;
    }
    
    @Override
    public void sacar(double valor) {
        if(this.saldo >= valor) {
            this.saldo -= valor;
        }
        if(this.saldo < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
    
    @Override
    public void pagar(double valor) {
        if(this.saldo >= valor) {
            this.saldo -= valor;
        }
        if(this.saldo < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
    
    @Override
    public void transferir(double valor, IContaCorrente contaDestino) {
        if(this.saldo >= valor) {
            
            this.saldo -= valor;
            contaDestino.depositar(valor);            
        }
        if(this.saldo < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
    
    @Override
    public void ativar() {
        this.ativa = true;
    }
    
    @Override
    public void desativar() {
        this.ativa = false;
    }
    
    @Override
    public String getNumero() {
        return this.numero;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public boolean isAtiva() {
        
        return ativa;
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

    private void verificaAtiva() {
        if(!isAtiva())
            throw new RuntimeException("Conta inativa");
    }
}
