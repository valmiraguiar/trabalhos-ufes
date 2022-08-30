package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observable;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.LoginView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Valmir Aguiar
 */
public class LoginPresenter implements Observable {

    private final LoginView view;
    private final UsuarioDAO usuarioDAO;
    private ArrayList<Observer> observers;

    public LoginPresenter(JDesktopPane desktop) {
        this.view = new LoginView();
        this.usuarioDAO = new UsuarioDAO();
        this.observers = new ArrayList<>();

        view.getBtnEntrar().addActionListener((e) -> {
            realizarLogin();
        });

        view.getBtnCadastro().addActionListener((e) -> {
            realizarCadastro(desktop);
        });

        desktop.add(this.view);

        this.view.setVisible(true);
    }

    private void realizarLogin() {
        String nomeUsuario = this.view.getTxtUsuario().getText();
        String senha = String.valueOf(this.view.getTxtPassSenha().getPassword());

        if (nomeUsuario.isEmpty() || nomeUsuario.isBlank() || senha.isEmpty() || senha.isBlank()) {
            JOptionPane.showMessageDialog(this.view, "Preencha os campos de usuário e senha!");
        } else {
            try {
                UsuarioModel usuario = this.usuarioDAO.login(nomeUsuario, senha);

                if (usuario == null) {
                    JOptionPane.showMessageDialog(this.view, "Erro de login! Confira seu nome de usuário e senha");
                    this.view.getTxtPassSenha().setText("");
                } else {
                                           
                    notificar(usuario);
                    JOptionPane.showMessageDialog(this.view, "Bem vindo! \n");

                }

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this.view, "Error: " + e.getMessage());
                this.view.getTxtPassSenha().setText("");
            }
        }

    }

    private void realizarCadastro(JDesktopPane desktop) {
        new CadastroUsuarioPresenter(desktop, false);
    }

    @Override
    public void registrarObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificar(Object object) {
        for(Observer observer : observers) {
            observer.update(object);
        }
    }

}
