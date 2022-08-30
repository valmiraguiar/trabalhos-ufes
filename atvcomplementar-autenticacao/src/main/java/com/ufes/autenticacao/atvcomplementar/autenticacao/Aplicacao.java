package com.ufes.autenticacao.atvcomplementar.autenticacao;

import com.ufes.autenticacao.atvcomplementar.autenticacao.model.ContaCorrenteProxy;
import com.ufes.autenticacao.atvcomplementar.autenticacao.model.Usuario;
import java.util.ArrayList;

public class Aplicacao {

    private final ArrayList<ContaCorrenteProxy> contasCadastradas = new ArrayList<>(); // SIMULA AS CONTAS DO SISTEMA
    private final ArrayList<Usuario> usuarios = new ArrayList<>(); // SIMULA OS USUARIOS DO SISTEMA
    
    public void realizarLogin(String nomeUsuario, String senha) { // SIMULA A REALIZAÇÃO DE LOGIN
        for (ContaCorrenteProxy conta : contasCadastradas) {
            conta.autenticar(nomeUsuario, senha);
        }
    }

    public void realizarLogin(String token) { // SIMULA A REALIZAÇÃO DE LOGIN
        for (ContaCorrenteProxy conta : contasCadastradas) {
            conta.autenticar(token);
        }
    }
    
    public void abrirConta(ContaCorrenteProxy contaCorrente) {        
        this.contasCadastradas.add(contaCorrente);
    }
    
    public ArrayList<ContaCorrenteProxy> getContasCadastradas() {
        return this.contasCadastradas;
    }
    
    public ArrayList<Usuario> getUsuarios() {
        return this.usuarios;
    }
    
    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }
    
    public ContaCorrenteProxy getConta(String numeroConta) {
        for(ContaCorrenteProxy conta : this.contasCadastradas) {
            if(conta.getNumero().equals(numeroConta))
                return conta;
        }
        
        throw new RuntimeException("Conta não encontrada!");
    }
}
