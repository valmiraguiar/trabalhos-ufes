package com.ufes.pss.trabalhofinal.state;

import com.ufes.pss.trabalhofinal.presenter.MainPresenter;

/**
 *
 * @author Valmir Aguiar
 */
public class Logado extends LoginState{
    
    public Logado(MainPresenter mainPresenter, boolean administrador) {
        super(mainPresenter, administrador);
        super.mainPresenter.setViewUsuarioLogado(true, administrador);
    }
    
    @Override
    public void deslogar() {
        super.mainPresenter.setViewUsuarioLogado(false, super.administrador);
    }
    
}
