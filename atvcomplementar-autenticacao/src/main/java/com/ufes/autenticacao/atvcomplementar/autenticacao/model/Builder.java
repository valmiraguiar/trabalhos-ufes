package com.ufes.autenticacao.atvcomplementar.autenticacao.model;

import java.util.Set;

public interface Builder {
   
    public void addAutorizado(boolean autorizado);
    public void addAutenticado(boolean autenticado);
    public void addAutorizacao(String autorizacao);
}
