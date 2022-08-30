package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

public class ClienteBuilder implements Builder{
    
    private Usuario usuario;
    
    public ClienteBuilder(String nome, String nomeUsuario, String senha) {
        this.usuario = new Usuario(nome, "Cliente", nomeUsuario, senha);
    }

    @Override
    public void addAutorizado(boolean autorizado) {
        usuario.setAutorizado(autorizado);
    }

    @Override
    public void addAutenticado(boolean autenticado) {
        usuario.setAutenticado(autenticado);
    }

    @Override
    public void addAutorizacao(String autorizacao) {
        usuario.add(autorizacao);
    }
    
    public Usuario getResultado() {
        return this.usuario;
    }
}
