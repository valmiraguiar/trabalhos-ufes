package com.ufes.pss.trabalhofinal.state;

import com.ufes.pss.trabalhofinal.presenter.MainPresenter;

/**
 *
 * @author Valmir Aguiar
 */
public abstract class LoginState {

    protected MainPresenter mainPresenter;
    protected boolean administrador;

    public LoginState(MainPresenter mainPresenter, boolean administrador) {
        this.mainPresenter = mainPresenter;
        this.administrador = administrador;
    }

    public void logar() throws Exception {

    }

    public void deslogar() throws Exception {

    }

}
