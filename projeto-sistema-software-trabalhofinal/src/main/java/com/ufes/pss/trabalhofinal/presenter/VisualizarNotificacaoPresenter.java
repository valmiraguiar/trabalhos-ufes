package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.Imagem;
import com.ufes.pss.trabalhofinal.dao.UsuarioImagemDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioImagemModel;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.VisualizarNotificacaoView;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class VisualizarNotificacaoPresenter {

    private VisualizarNotificacaoView view;
    private ArrayList<Observer> observers;

    private UsuarioModel usuarioLogado;
    private JDesktopPane mainDesktop;
    private UsuarioImagemDAO usuarioImgDAO;
    private UsuarioImagemModel usuarioImagem;

    public VisualizarNotificacaoPresenter(JDesktopPane desktop, String path, UsuarioModel usuarioLogado, UsuarioImagemModel usuarioImg) {
        view = new VisualizarNotificacaoView();
        this.usuarioLogado = usuarioLogado;
        this.mainDesktop = desktop;
        this.usuarioImgDAO = new UsuarioImagemDAO();
        this.usuarioImagem = usuarioImg;

        this.view.getBtnFechar().addActionListener((e) -> {
            view.dispose();
        });

        this.view.getBtnOk().addActionListener((e) -> {
            ok();
        });

        this.view.getBtnFechar().addActionListener((e) -> {
            this.view.dispose();
        });

        carregarImg(path);

        desktop.add(this.view);
        this.view.setVisible(true);
    }

    private void carregarImg(String path) {
        try {
            Imagem imagem = new Imagem(path);
            this.view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, "Erro ao carregar imagem!\n" + e.getMessage());
        }
    }

    private void ok() {
        try {
            this.usuarioImgDAO.excluirById(this.usuarioImagem.getIdUsuarioImagem());
            
            new NotificacoesPresenter(this.mainDesktop, this.usuarioLogado);
            this.view.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, "Erro ao marcar notificação como visualizada!\n" + e.getMessage());
        }
    }
}
