package com.ufes.pss.trabalhofinal.model;

import com.ufes.pss.trabalhofinal.presenter.Memento;
import java.util.Stack;

public class Zelador {
    private final Stack<Memento> historico;
    private static Zelador instancia;
    
    private Zelador() {
        this.historico = new Stack<>();
    }
    
    public static Zelador getInstancia() {
       if (instancia == null) {
           instancia = new Zelador();
       }
       return instancia;
   }

   public void add(Memento estado) throws Exception {
       this.historico.push(estado);

   }

   public Memento getUltimo() throws Exception {
       if (!historico.isEmpty()) {
           return historico.pop();
       }
       throw new Exception("Não há estados salvos");
   }

}
