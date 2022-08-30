package com.ufes.pss.trabalhofinal.state;

import com.ufes.pss.trabalhofinal.presenter.MainPresenter;

/**
 *
 * @author Valmir Aguiar
 */
public class Deslogado extends LoginState {

    public Deslogado(MainPresenter mainPresenter, boolean administrador) {
        super(mainPresenter, administrador);
        super.mainPresenter.setViewUsuarioLogado(false, administrador);
    }

    @Override
    public void logar() throws Exception {
        super.mainPresenter.setViewUsuarioLogado(true, super.administrador);
    }

}
