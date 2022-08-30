package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private final String nome;
    private String perfil;
    private final String nomeUsuario;
    private final String senha;
    private boolean autorizado = false;
    private boolean autenticado = false;
    private Set<String> autorizacoes;

    public Usuario(String nome, String perfil, String nomeUsuario, String senha) {
        this.nome = nome;
        this.perfil = perfil;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        autorizacoes = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    protected void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    protected void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public Set<String> getAutorizacoes() {
        return autorizacoes;
    }
    
    protected void add(String autorizacao) {
        this.autorizacoes.add(autorizacao);
    }

    public String getPerfil() {
        return perfil;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nome=" + nome + ", perfil=" + perfil + ", nomeUsuario=" + nomeUsuario + ", senha=" + senha + ", autorizado=" + autorizado + ", autenticado=" + autenticado + ", autorizacoes=" + autorizacoes + '}';
    }
    
    
}
