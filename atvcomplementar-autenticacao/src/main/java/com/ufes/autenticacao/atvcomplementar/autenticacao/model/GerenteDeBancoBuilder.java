package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

public class GerenteDeBancoBuilder implements Builder{
    
    private Usuario usuario;
    
    public GerenteDeBancoBuilder(String nome, String nomeUsuario, String senha) {
        this.usuario = new Usuario(nome, "Gerente de Banco", nomeUsuario, senha);
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
