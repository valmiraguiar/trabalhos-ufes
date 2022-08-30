package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.dao.UsuarioImagemDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioImagemModel;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observable;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.NotificacoesView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class NotificacoesPresenter implements Observable {

    private NotificacoesView view;
    private ArrayList<UsuarioImagemModel> usuarioImgs;
    private ArrayList<Observer> observers;
    private UsuarioImagemDAO usuarioImgDAO;
    private UsuarioModel usuarioLogado;
    private JDesktopPane mainDesktop;

    public NotificacoesPresenter(JDesktopPane desktop, UsuarioModel usuarioLogado) {
        this.mainDesktop = desktop;
        this.view = new NotificacoesView();
        this.usuarioImgs = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.usuarioImgDAO = new UsuarioImagemDAO();
        this.usuarioLogado = usuarioLogado;

        this.view.getBtnFechar().addActionListener((e) -> {
            this.view.dispose();
        });

        this.view.getBtnVisualizar().addActionListener((e) -> {
            visualizarSelecionado();
        });

        carregarNotificacoes();

        desktop.add(this.view);

        this.view.setVisible(true);
    }

    private void carregarNotificacoes() {
        try {
            this.usuarioImgs = usuarioImgDAO.getUsuarioImgs(this.usuarioLogado.getIdUsuario());
            notificar(this.usuarioLogado);
            setTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void setTable() {

        this.view.getTblNotificacoes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ArrayList<UsuarioModel> usuariosSrc = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            for (UsuarioImagemModel usuarioImg : this.usuarioImgs) {
                usuariosSrc.add(usuarioDAO.getUsuarioById(usuarioImg.getIdUsuarioSrc()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, "Erro ao buscar usuarios!\n" + e.getMessage());
        }

        DefaultTableModel tblModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Usuário", "Caminho da Imagem"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblModel.setNumRows(0);

        int i = 0;
        for (UsuarioImagemModel usuarioImg : this.usuarioImgs) {
            String permissao = "";
            String nomeUsuario;

            tblModel.addRow(
                    new Object[]{
                        usuariosSrc.get(i).getNome(),
                        usuarioImg.getPath()
                    }
            );
        }

        this.view.getTblNotificacoes().setModel(tblModel);
    }

    private void visualizarSelecionado() {
        int idSelecionado = this.view.getTblNotificacoes().getSelectedRow();

        try {
            new VisualizarNotificacaoPresenter(
                    this.mainDesktop,
                    this.usuarioImgs.get(idSelecionado).getPath(),
                    this.usuarioLogado,
                    this.usuarioImgs.get(idSelecionado)
            );

            this.view.dispose();
            //notificar(this.usuarioLogado);
            //setTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
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
        for (Observer observer : observers) {
            observer.update(object);
        }
    }

}
