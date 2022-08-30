package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.CadastroUsuarioView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class CadastroUsuarioPresenter {

    private CadastroUsuarioView view;
    private UsuarioDAO usuarioDAO;
    private ArrayList<Observer> observers;

    public CadastroUsuarioPresenter(JDesktopPane desktop, boolean srcUsuarios) {
        this.view = new CadastroUsuarioView();
        this.usuarioDAO = new UsuarioDAO();
        this.observers = new ArrayList<>();

        this.view.getBtnCancelar().addActionListener((e) -> {
            if (!srcUsuarios) {
                cancelarCadastro(desktop);
            } else {
                new UsuariosPresenter(desktop);
                this.view.dispose();
            }
        });

        this.view.getBtnCadastrar().addActionListener((e) -> {
            realizarCadastro(desktop, srcUsuarios);
        });

        desktop.add(this.view);
        this.view.setVisible(true);
    }

    private void cancelarCadastro(JDesktopPane desktop) {
        this.view.dispose();
    }

    private void realizarCadastro(JDesktopPane desktop, boolean srcUsuarios) {
        String nome = this.view.getTxtNomeCompleto().getText();
        String nomeUsuario = this.view.getTxtUsuarioNome().getText();
        String senha = String.valueOf(this.view.getTxtSenha().getPassword());
        boolean tipoConta = false;

        if (nome.isEmpty()
                || nome.isBlank()
                || nomeUsuario.isEmpty()
                || nomeUsuario.isBlank()
                || senha.isEmpty()
                || senha.isBlank()) {

            JOptionPane.showMessageDialog(this.view, "Preencha todos os campos!");

        } else {
            try {
                UsuarioModel usuario = new UsuarioModel(nome, nomeUsuario, senha, tipoConta, false, false);
                this.usuarioDAO.insertUsuario(usuario, true, usuario.isAdministrador());

                JOptionPane.showMessageDialog(this.view, "Usuário cadastrado com sucesso!");
                if(srcUsuarios) {
                    new ManterUsuarioPresenter(usuario, desktop, true); 
                    this.view.dispose();
                }
                
                limparCampos();
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this.view, "Error: " + e.getMessage());
                this.view.getTxtSenha().setText("");
            }
        }
    }

    private void limparCampos() {
        this.view.getTxtNomeCompleto().setText("");
        this.view.getTxtSenha().setText("");
        this.view.getTxtUsuarioNome().setText("");
    }
}
