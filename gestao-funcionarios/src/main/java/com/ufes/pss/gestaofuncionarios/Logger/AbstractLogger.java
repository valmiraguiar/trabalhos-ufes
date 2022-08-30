
package com.ufes.pss.gestaofuncionarios.Logger;

import java.util.ArrayList;


public abstract class AbstractLogger {
    private String nomeArquivo;
    
    public AbstractLogger(String nomeArquivo){
        this.nomeArquivo = nomeArquivo;
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    public abstract void logCRUDFuncionario(String nome, String operacao);

    public abstract void logConsultaBonus(String nome);

    public abstract void logCalculaSalario(ArrayList<String> funcionariosCalculados);

    public abstract void logFalha(String operacao, String excesao);
}
