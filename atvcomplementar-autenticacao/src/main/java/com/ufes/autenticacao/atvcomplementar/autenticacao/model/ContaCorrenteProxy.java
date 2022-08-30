package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

import com.ufes.autenticacao.atvcomplementar.autenticacao.token.TokenAuthenticator;
import java.util.Set;

public class ContaCorrenteProxy implements IContaCorrente{
    
    private IContaCorrente contaCorrenteReal;
    private Usuario usuarioGerenciador;
    
    public ContaCorrenteProxy(String numero, double saldoInicial, Usuario usuario) {
        contaCorrenteReal = new ContaCorrente(numero, saldoInicial, usuario);
    }

    @Override
    public void depositar(double valor) {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "depositar")) {
            contaCorrenteReal.depositar(valor);
        }
    }

    @Override
    public void sacar(double valor) {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "sacar")) {
            contaCorrenteReal.sacar(valor);
        }
    }

    @Override
    public void pagar(double valor) {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "pagar")) {
            contaCorrenteReal.pagar(valor);
        }
    }

    @Override
    public void transferir(double valor, IContaCorrente contaDestino) {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "transferir")) {
            contaCorrenteReal.transferir(valor, contaDestino);
        }
    }

    @Override
    public void ativar() {
        if(verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "ativar")) {
            contaCorrenteReal.ativar();
        }
    }

    @Override
    public void desativar() {
        if(verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "desativar")) {
            contaCorrenteReal.desativar();
        }
    }

    @Override
    public String getNumero() {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "getNumero") || verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "getNumero")) {
            return contaCorrenteReal.getNumero();
        }
        throw new RuntimeException("Usuario não autorizado a realizar esse tipo de operação!");
    }

    @Override
    public double getSaldo() {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "getSaldo") || verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "getSaldo")) {
            return contaCorrenteReal.getSaldo();
        }
        throw new RuntimeException("Usuario não autorizado a realizar esse tipo de operação!");
    }

    @Override
    public boolean isAtiva() {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "isAtiva") || verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "isAtiva")) {
            return contaCorrenteReal.isAtiva();
        }
        throw new RuntimeException("Usuario não autorizado a realizar esse tipo de operação!");
    }

    @Override
    public Usuario getUsuario() {
        if(verificaAutorizacao(contaCorrenteReal.getUsuario().getAutorizacoes(), "getUsuario") || verificaAutorizacao(usuarioGerenciador.getAutorizacoes(), "getUsuario")) {
            return contaCorrenteReal.getUsuario();
        }
        throw new RuntimeException("Usuario não autorizado a realizar esse tipo de operação!");
    }

    public IContaCorrente autenticar(String nomeUsuario, String senha) {        
        if(nomeUsuario.equals(contaCorrenteReal.getUsuario().getNomeUsuario()) && senha.equals(contaCorrenteReal.getUsuario().getSenha())) {
            contaCorrenteReal.getUsuario().setAutenticado(true);
            return this;
        } else {
            throw new RuntimeException("Authentication Failed!");
        }
    }
    
    public IContaCorrente autenticar(String token) {
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator();
        String tokenDecoded = tokenAuthenticator.decodificarToken(token);
        
        String usuarioSenha;
        
        usuarioSenha = contaCorrenteReal.getUsuario().getNomeUsuario() + "-" + contaCorrenteReal.getUsuario().getSenha();
        
        if(usuarioSenha.equals(tokenDecoded)) {
            contaCorrenteReal.getUsuario().setAutenticado(true);
            return this;
        } else {
            throw new RuntimeException("Authentication Failed!");
        }
    }
    
    public boolean verificaAutorizacao(Set<String> autorizacoes, String operacao) {
        for(String autorizacao : autorizacoes) {
            if(autorizacao.equals(operacao)) {
                return true;
            }
        }
        throw new RuntimeException("User authorization not found!");
    }

    @Override
    public String toString() {
        return "ContaCorrente{" + "numero=" + contaCorrenteReal.getNumero() + 
                ", saldo=" + contaCorrenteReal.getSaldo() + 
                ", ativa=" + contaCorrenteReal.isAtiva() + 
                ", usuario= [" + contaCorrenteReal.getUsuario().toString() + "]}";
    }
    
    public void setGerente(Usuario usuario) {
        this.usuarioGerenciador = usuario;
    }
    
}
