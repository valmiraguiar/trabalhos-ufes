package com.ufes.pss.trabalhofinal.observer;

public interface Observable {
    public void registrarObserver(Observer observer);
    
    public void removerObserver(Observer observer);
    
    public void notificar(Object object);
}
