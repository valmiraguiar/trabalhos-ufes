package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observable;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.UsuariosPermissaoView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class UsuariosPermissaoPresenter implements Observable {

    private UsuariosPermissaoView view;
    private ArrayList<UsuarioModel> usuarios;
    private ArrayList<Observer> observers;
    private UsuarioDAO usuarioDAO;
    private UsuarioModel usuarioLogado;

    public UsuariosPermissaoPresenter(JDesktopPane desktop, UsuarioModel usuario) {
        this.view = new UsuariosPermissaoView();
        this.usuarios = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioLogado = usuario;

        this.view.getBtnFechar().addActionListener((e) -> {            
            this.view.dispose();
        });

        this.view.getBtnAutorizar().addActionListener((e) -> {
            autorizarSelecionado();
        });

        carregarUsuarios();

        desktop.add(this.view);

        this.view.setVisible(true);
    }

    private void carregarUsuarios() {
        try {
            this.usuarios = usuarioDAO.getTodosUsuariosPedindoPermissao();
            setTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void setTable() {

        this.view.getTblUsuarios().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel tblModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Nome", "Usuário", "Permissão Requerida"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblModel.setNumRows(0);

        for (UsuarioModel usuario : this.usuarios) {
            String permissao = "";

            if (usuario.isAdministrador()) {
                permissao = "Administrador";
            } else {
                permissao = "Usuário Comum";
            }

            tblModel.addRow(
                    new Object[]{
                        usuario.getNome(),
                        usuario.getNomeUsuario(),
                        permissao
                    }
            );
        }

        this.view.getTblUsuarios().setModel(tblModel);
    }

    private void autorizarSelecionado() {
        int idSelecionado = this.view.getTblUsuarios().getSelectedRow();
        UsuarioModel usuario = this.usuarios.get(idSelecionado);
        usuario.setPermissaoSolicitada(false);
        usuario.setAutorizado(true);

        try {
            this.usuarioDAO.update(usuario);

            JOptionPane.showMessageDialog(this.view, "Usuário autorizado com sucesso!");

            this.usuarios.clear();
            this.usuarios = this.usuarioDAO.getTodosUsuariosPedindoPermissao();
            notificar(this.usuarioLogado);
            setTable();
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
