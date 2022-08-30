package com.ufes.pss.gestaofuncionarios.presenter;

import com.ufes.pss.gestaofuncionarios.collection.FuncionarioCollection;
import com.ufes.pss.gestaofuncionarios.factory.SistemaDeLogs;
import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import com.ufes.pss.gestaofuncionarios.view.BuscarFuncionarioView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class BuscarFuncionarioPresenter {

    private BuscarFuncionarioView view;
    private final DefaultTableModel tmFuncionarios;
    private final FuncionarioCollection funcionarios;
    private SistemaDeLogs logs;
    
    public BuscarFuncionarioPresenter(PrincipalPresenter principal, FuncionarioCollection funcionarios, SistemaDeLogs logs) {
        this.funcionarios = funcionarios;
        this.logs = logs;
        funcionarios.ordenar();
        view = new BuscarFuncionarioView();
        principal.getView().getDesktop().add(view);

        tmFuncionarios = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "Idade", "Função", "Salário base (R$)"}
        ) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        view.getTblFuncionarios().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        view.getTblFuncionarios().setModel(tmFuncionarios);
        
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            fechar(principal);
        });

        view.getBtnBusca().addActionListener((ActionEvent ae) -> {
            buscar(funcionarios);
        });

        view.getBtnNovo().addActionListener((ActionEvent ae) -> {
            novo(principal, funcionarios);
        });

        view.getBtnBonus().addActionListener((ActionEvent ae) -> {
            if (view.getTblFuncionarios().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Selecione uma linha");
            } else {
                bonus(principal, funcionarios);
            }
        });

        view.getBtnVisualizar().addActionListener((ActionEvent ae) -> {
            if (view.getTblFuncionarios().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(view, "Selecione uma linha");
            } else {
                visualizar(principal, funcionarios);
            }
        });

        view.getBtnTodosFuncionarios().addActionListener((ActionEvent ae) -> {
            listarTodosFuncionarios();
        });
        
        view.setVisible(true);
    }

    private void fechar(PrincipalPresenter pp) {
        view.dispose();
        pp.updateNumFuncionarios();
    }

    private void listarTodosFuncionarios() {
        eraseTable();
        
        for (Funcionario f : funcionarios.getFuncionarios()) {
            tmFuncionarios.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getIdade(),
                f.getCargo(),
                String.format("%.2f", f.getSalario()).replace(".", ",")
            });
        }
    }

    private void buscar(FuncionarioCollection funcionarios) {

        eraseTable();

        String busca = view.getTxtBusca().getText().toLowerCase();

        if (busca != null && !"".equals(busca)) {

            for (Funcionario f : funcionarios.getFuncionarios()) {
                if (f.getNome().toLowerCase().contains(busca)) {
                    tmFuncionarios.addRow(new Object[]{
                        f.getId(),
                        f.getNome(),
                        f.getIdade(),
                        f.getCargo(),
                        String.format("%.2f", f.getSalario()).replace(".", ",")
                    });
                }
            }
        } else {
            String msg = "Forneça um nome para busca!";
            logs.getLogger().logFalha("Busca", msg);
            throw new RuntimeException(msg);
        }

        view.getTblFuncionarios().setModel(tmFuncionarios);
    }

    private void novo(PrincipalPresenter principal, FuncionarioCollection funcionarios) {
        ManterFuncionarioPresenter manterFuncionarioPresenter = new ManterFuncionarioPresenter(principal, funcionarios, logs);
        eraseTable();
    }

    private void bonus(PrincipalPresenter principal, FuncionarioCollection funcionarios) {
        int id = Integer.parseInt(view.getTblFuncionarios().getValueAt(view.getTblFuncionarios().getSelectedRow(), 0).toString());
        Funcionario f = funcionarios.findById(id);

        new VerBonusPresenter(principal, f, logs);
        eraseTable();
        view.getTxtBusca().setText("");
    }

    private void visualizar(PrincipalPresenter principal, FuncionarioCollection funcionarios) {
        int id = Integer.parseInt(view.getTblFuncionarios().getValueAt(view.getTblFuncionarios().getSelectedRow(), 0).toString());
        Funcionario f = funcionarios.findById(id);
        new ManterFuncionarioPresenter(principal, funcionarios, f, logs);
        //eraseTable();
        view.getTxtBusca().setText("");
    }

    private void eraseTable() {
        tmFuncionarios.setNumRows(0);
        view.getTblFuncionarios().setModel(tmFuncionarios);

    }

}
