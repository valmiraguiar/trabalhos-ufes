package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.view.ManterUsuarioView;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class ManterUsuarioPresenter {

    private final ManterUsuarioView view;
    private UsuarioModel usuario;
    private UsuarioDAO usuarioDAO;

    public ManterUsuarioPresenter(UsuarioModel usuario, JDesktopPane desktop, boolean srcVisualizacao) {
        this.view = new ManterUsuarioView();
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();

        init();

        if(!srcVisualizacao) {
            this.view.getBtnExcluir().setVisible(false);
            this.view.getBtnExcluir().setEnabled(false);
        }
        
        this.view.getBtnEditar().addActionListener((e) -> {
            editar();
        });

        this.view.getBtnCancelar().addActionListener((e) -> {
            this.view.dispose();
            if(srcVisualizacao)                
                new UsuariosPresenter(desktop);
        });

        this.view.getBtnSalvar().addActionListener((e) -> {
            salvarEdicao();
            init();
        });

        this.view.getBtnExcluir().addActionListener((e) -> {
            excluirUsuario(usuario.getIdUsuario(), desktop);
        });
        
        desktop.add(this.view);
        this.view.setVisible(true);
    }

    private void init() {
        setTxtDisable();

        this.view.getTxtNome().setText(this.usuario.getNome());
        this.view.getTxtNomeUsuario().setText(this.usuario.getNomeUsuario());
        this.view.getTxtSenha().setText(this.usuario.getSenha());

        this.view.getCboxTipoConta().setEnabled(false);
        this.view.getCboxTipoConta().setSelectedIndex(0);
        if (this.usuario.isAdministrador()) {
            this.view.getCboxTipoConta().setSelectedIndex(1);
        }

        this.view.getBtnSalvar().setEnabled(false);
    }

    private void setTxtEnable() {
        this.view.getTxtNome().setEnabled(true);
        this.view.getTxtNomeUsuario().setEnabled(true);
        this.view.getTxtSenha().setEnabled(true);
    }

    private void setTxtDisable() {
        this.view.getTxtNome().setEnabled(false);
        this.view.getTxtNomeUsuario().setEnabled(false);
        this.view.getTxtSenha().setEnabled(false);
    }

    private void editar() {
        setTxtEnable();
        this.view.getBtnSalvar().setEnabled(true);
        this.view.getBtnEditar().setEnabled(false);
    }

    private void salvarEdicao() {
        try {
            this.usuario.setNome(this.view.getTxtNome().getText());
            this.usuario.setSenha(this.view.getTxtSenha().getText());
            this.usuario.setNomeUsuario(this.view.getTxtNomeUsuario().getText());

            this.usuarioDAO.update(usuario);
        } catch (Exception e) {
            setTxtDisable();
            this.view.getBtnSalvar().setEnabled(false);            
            
            JOptionPane.showMessageDialog(this.view, "Ocorreu algum erro ao salvar!\n" + e.getMessage());
        } finally {
            this.view.getBtnEditar().setEnabled(true);
        }
    }
    
    private void excluirUsuario(int idUsuario, JDesktopPane desktop) {
        
        String message = "Deseja realmente excluir?";
        String title = "Confirmação de exclusão";

        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            try{
                this.usuarioDAO.excluirUsuario(idUsuario);
                
                JOptionPane.showMessageDialog(this.view, "Usuário excluído com sucesso");
                
                new UsuariosPresenter(desktop);
                this.view.dispose();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this.view, e.getMessage());
            }
        }
    }
}
