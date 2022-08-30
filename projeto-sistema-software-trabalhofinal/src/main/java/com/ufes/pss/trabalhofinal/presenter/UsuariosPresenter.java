package com.ufes.pss.trabalhofinal.presenter;

import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.view.UsuariosView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class UsuariosPresenter {

    private UsuariosView view;
    private ArrayList<UsuarioModel> usuarios;
    private UsuarioDAO usuarioDAO;
    private JDesktopPane mainDesktop;

    public UsuariosPresenter(JDesktopPane desktop) {
        this.view = new UsuariosView();
        this.usuarioDAO = new UsuarioDAO();
        this.mainDesktop = desktop;

        loadUsuarios();

        this.view.getBtnFechar().addActionListener((e) -> {
            this.view.dispose();
        });

        this.view.getBtnBuscar().addActionListener((e) -> {
            buscar();
        });

        this.view.getBtnVisualizar().addActionListener((e) -> {
            visualizar();
        });

        this.view.getBtnNovo().addActionListener((e) -> {            
            this.view.dispose();            
            new CadastroUsuarioPresenter(desktop, true);
        });

        this.view.getBtnAdmin().addActionListener((e) -> {
            tornarAdmin();
        });
        
        setTable();
        desktop.add(this.view);
        this.view.setVisible(true);
    }

    private void loadUsuarios() {
        try {
            this.usuarios = this.usuarioDAO.getUsuarios();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void setTable() {

        this.view.getTblUsuarios().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel tblModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Nome", "Usuário", "Permissão"}) {
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
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getNomeUsuario(),
                        permissao
                    }
            );
        }

        this.view.getTblUsuarios().setModel(tblModel);
    }

    private void buscar() {
        String txtBusca = this.view.getTxtBusca().getText();

        if (txtBusca.isBlank() || txtBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this.view, "Digite algo!");
        } else {
            switch (this.view.getCboxBuscaPor().getSelectedIndex()) {
                case 0:
                    buscarPorNome(txtBusca);
                    break;
                case 1:
                    buscarPorNomeUsuario(txtBusca);
                    break;
                case 2:
                    buscarPorId(txtBusca);
                    break;
            }
        }

    }

    private void buscarPorNome(String txtBusca) {
        try {
            this.usuarios.clear();
            this.usuarios = this.usuarioDAO.getUsuarioByNome(txtBusca);

            if (!this.usuarios.isEmpty()) {
                setTable();
            } else {
                JOptionPane.showMessageDialog(this.view, "Nada encontrado!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void buscarPorId(String txtBusca) {
        try {
            UsuarioModel result = this.usuarioDAO.getUsuarioById(Integer.parseInt(txtBusca));
            this.usuarios.clear();
            this.usuarios.add(result);
            if (!this.usuarios.isEmpty()) {
                setTable();
            } else {
                JOptionPane.showMessageDialog(this.view, "Nada encontrado!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void buscarPorNomeUsuario(String txtBusca) {
        try {
            this.usuarios.clear();
            this.usuarios = this.usuarioDAO.getUsuarioByNomeUsuario(txtBusca);

            if (!this.usuarios.isEmpty()) {
                setTable();
            } else {
                JOptionPane.showMessageDialog(this.view, "Nada encontrado!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }

    private void visualizar() {
        int selected = this.view.getTblUsuarios().getSelectedRow();

        new ManterUsuarioPresenter(this.usuarios.get(selected), this.mainDesktop, true);
        this.view.dispose();
    }
    
    private void tornarAdmin() {
        try {
            int selected = this.view.getTblUsuarios().getSelectedRow();
            
            UsuarioModel usuarioSelecionado = this.usuarios.get(selected);
            usuarioSelecionado.setAdministrador(true);
            
            usuarioDAO.update(usuarioSelecionado);
            
            JOptionPane.showMessageDialog(this.view, "Permissão de administrador concedida ao usuário "+usuarioSelecionado.getNome()+"!");
            loadUsuarios();
            setTable();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }        
    }
}
