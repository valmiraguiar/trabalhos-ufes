package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.Imagem;
import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.dao.UsuarioImagemDAO;
import com.ufes.pss.trabalhofinal.model.MiniaturaFileChooser;
import com.ufes.pss.trabalhofinal.model.UsuarioImagemModel;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.state.Deslogado;
import com.ufes.pss.trabalhofinal.state.Logado;
import com.ufes.pss.trabalhofinal.view.MainView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class MainPresenter implements Observer {

    private final MainView view;
    private UsuarioDAO usuarioDAO;
    private UsuarioImagemDAO usuarioImagemDAO;
    private UsuarioModel usuarioLogado;

    public MainPresenter() {
        this.view = new MainView();
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioDAO.criarTabelaUsuario();
        this.usuarioImagemDAO = new UsuarioImagemDAO();
        this.usuarioImagemDAO.criarTabelaImagem();
        this.usuarioLogado = null;

        this.view.setLocationRelativeTo(this.view.getParent());

        this.view.getMenuUsuario().setEnabled(false);
        this.view.getMenuArquivos().setEnabled(false);
        this.view.getBtnNotificacoes().setEnabled(false);
        this.view.getBtnSolicitacoesAcesso().setVisible(false);

        this.view.getItemSair().addActionListener((e) -> {
            sair();
        });

        this.view.getItemEntrar().addActionListener((e) -> {
            limparTelas();
            new LoginPresenter(this.view.getDesktopMain()).registrarObserver(this);
        });

        this.view.getItemGerenciamentoUsuario().addActionListener((e) -> {
            new UsuariosPresenter(this.view.getDesktopMain());
        });

        this.view.getBtnSolicitacoesAcesso().addActionListener((e) -> {
            solicitacoesAcesso();
        });

        this.view.getItemVisualizarImagem().addActionListener((e) -> {
            visualizarImagem();
        });

        this.view.getItemAtualizacaoCadastro().addActionListener((e) -> {
            new ManterUsuarioPresenter(usuarioLogado, this.view.getDesktopMain(), false);
        });

        this.view.getBtnNotificacoes().addActionListener((e) -> {
            new NotificacoesPresenter(this.view.getDesktopMain(), this.usuarioLogado).registrarObserver(this);
        });

        inicioLogin();

        this.view.setVisible(true);
    }

    private void sair() {

        String message = "Deseja realmente sair?";
        String title = "Sair";

        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                limparTelas();
                new Deslogado(this, false);
                update(null);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.view, "ERROR -> " + e.getMessage());
            }
        }

    }

    private void limparTelas() {
        for (JInternalFrame f : this.view.getDesktopMain().getAllFrames()) {
            f.dispose();
        }
    }

    private void inicioLogin() {
        new LoginPresenter(this.view.getDesktopMain()).registrarObserver(this);
    }

    public void setViewUsuarioLogado(boolean logado, boolean administrador) {
        if (logado) {
            if (administrador) { // admin
                this.view.getMenuUsuario().setEnabled(true);
                this.view.getItemVisualizarImagem().setVisible(true);
                this.view.getItemEntrar().setVisible(false);
                this.view.getItemSair().setVisible(true);
                this.view.getItemAtualizacaoCadastro().setVisible(true);
                this.view.getItemGerenciamentoUsuario().setVisible(true);

                this.view.getMenuArquivos().setEnabled(true);
                this.view.getItemVisualizarImagem().setVisible(true);
                this.view.getBtnSolicitacoesAcesso().setVisible(true);
            } else { //comum
                this.view.getMenuUsuario().setEnabled(true);
                this.view.getItemVisualizarImagem().setVisible(true);
                this.view.getItemEntrar().setVisible(false);
                this.view.getItemSair().setVisible(true);
                this.view.getItemAtualizacaoCadastro().setVisible(true);
                this.view.getItemGerenciamentoUsuario().setVisible(false);
                this.view.getBtnSolicitacoesAcesso().setVisible(false);

                this.view.getMenuArquivos().setEnabled(true);
                this.view.getItemVisualizarImagem().setVisible(true);
            }
        } else { //deslogado
            this.view.getMenuArquivos().setEnabled(false);
            this.view.getMenuUsuario().setEnabled(false);
            this.view.getBtnSolicitacoesAcesso().setVisible(false);
        }
    }

    @Override
    public void update(Object object) {
        if (object == null) {
            limparTelas();
            this.usuarioLogado = null;
            updateRodape();
            updateSolicitacoes();
            limparNotificacoes();
            new LoginPresenter(this.view.getDesktopMain()).registrarObserver(this);
            new Deslogado(this, false);
        } else {
            this.usuarioLogado = (UsuarioModel) object;
            updateRodape();
            updateSolicitacoes();
            updateNotificacoes();
            limparTelas();
            new Logado(this, this.usuarioLogado.isAdministrador());
        }
    }

    private void updateRodape() {
        if (this.usuarioLogado == null) {
            this.view.getTxtUsuario().setText("");
        } else {
            String tipo = "";
            if (this.usuarioLogado.isAdministrador()) {
                tipo = "Administrador -";
            } else {
                tipo = "Usuário Comum -";
            }

            this.view.getTxtUsuario().setText(tipo + " " + this.usuarioLogado.getNome());
        }
    }

    private void limparNotificacoes() {
        this.view.getTxtNotificacoes().setText("0 Notificações");
    }

    private void updateSolicitacoes() {
        try {
            ArrayList<UsuarioModel> temp = this.usuarioDAO.getTodosUsuariosPedindoPermissao();
            String txt = "Solicitações de acesso (" + temp.size() + ")";
            this.view.getBtnSolicitacoesAcesso().setText(txt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void updateNotificacoes() {
        try {
            ArrayList<UsuarioImagemModel> temp = this.usuarioImagemDAO.getUsuarioImgs(this.usuarioLogado.getIdUsuario());
            String txt = temp.size() + " Notificações";

            if (!temp.isEmpty()) {
                this.view.getBtnNotificacoes().setEnabled(true);
            }

            this.view.getTxtNotificacoes().setText(txt);
        } catch (Exception e) {
            this.view.getTxtNotificacoes().setText("0 Notificações");
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void solicitacoesAcesso() {
        new UsuariosPermissaoPresenter(this.view.getDesktopMain(), this.usuarioLogado).registrarObserver(this);
    }

    private void visualizarImagem() {
        if (!this.usuarioLogado.isAutorizado()) {

            String message = "Deseja solicitar permissão?";
            String title = "É necessário permissão para acessar as imagens";

            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    this.usuarioLogado.setPermissaoSolicitada(true);
                    this.usuarioDAO.update(usuarioLogado);

                    JOptionPane.showMessageDialog(this.view, "Aguarde a aprovação!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this.view, e.getMessage());
                }
            }

        } else {

            int response;
            MiniaturaFileChooser fileChooser = new MiniaturaFileChooser(new File("images"));
            response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File path = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String pathToCopy = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    Imagem imagem = new Imagem(path.toString());
                    new VisualizarImagemPresenter(this.view.getDesktopMain(), imagem, path, this.usuarioLogado);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }

        }
    }

    private void excluirUsuario(int idUsuario, JDesktopPane desktop) {

        String message = "Deseja realmente excluir?";
        String title = "Confirmação de exclusão";

        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                this.usuarioDAO.excluirUsuario(idUsuario);

                JOptionPane.showMessageDialog(this.view, "Usuário excluído com sucesso");

                new UsuariosPresenter(desktop);
                this.view.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.view, e.getMessage());
            }
        }
    }
}
