package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

public class Diretor {
    
    public void criarCliente(Builder builder) {
        builder.addAutenticado(false);
        builder.addAutorizado(false);
        builder.addAutorizacao("sacar");
        builder.addAutorizacao("depositar");
        builder.addAutorizacao("transferir");
        builder.addAutorizacao("pagar");
        builder.addAutorizacao("getUsuario");
        builder.addAutorizacao("getSaldo");
        builder.addAutorizacao("getNumero");
        builder.addAutorizacao("isAtiva");
    }
    
    public void criarGerenteDeBanco(Builder builder) {
        builder.addAutenticado(false);
        builder.addAutorizado(true);
        builder.addAutorizacao("ativar");
        builder.addAutorizacao("desativar");
        builder.addAutorizacao("getUsuario");
        builder.addAutorizacao("getSaldo");
        builder.addAutorizacao("getNumero");
    }
}
