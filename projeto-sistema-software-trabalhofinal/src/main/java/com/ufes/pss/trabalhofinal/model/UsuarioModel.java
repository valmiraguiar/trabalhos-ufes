package com.ufes.pss.trabalhofinal.model;

public class UsuarioModel {
    
    private int idUsuario;
    private String nome;
    private String nomeUsuario;
    private String senha;
    private boolean administrador;
    private boolean autorizado;
    private boolean permissaoSolicitada;

    public UsuarioModel(int idUsuario, String nome, String nomeUsuario, String senha, boolean administrador, boolean autorizado, boolean permissaoSolicitada) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.administrador = administrador;
        this.autorizado = autorizado;
        this.permissaoSolicitada = permissaoSolicitada;
    }

    public UsuarioModel(String nome, String nomeUsuario, String senha, boolean administrador, boolean autorizado, boolean permissaoSolicitada) {
        this.idUsuario = -1;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.administrador = administrador;
        this.autorizado = autorizado;
        this.permissaoSolicitada = permissaoSolicitada;
    }

    public int getIdUsuario() {
        return idUsuario;
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

    public boolean isAdministrador() {
        return administrador;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public void setAprovado(boolean aprovado) {
        this.autorizado = aprovado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public boolean isPermissaoSolicitada() {
        return permissaoSolicitada;
    }

    public void setPermissaoSolicitada(boolean permissaoSolicitada) {
        this.permissaoSolicitada = permissaoSolicitada;
    }
    
}
