package com.ufes.pss.gestaofuncionarios.presenter;

import com.ufes.pss.gestaofuncionarios.collection.FuncionarioCollection;
import com.ufes.pss.gestaofuncionarios.factory.SistemaDeLogs;
import com.ufes.pss.gestaofuncionarios.model.BonusHistorico;
import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import com.ufes.pss.gestaofuncionarios.utils.CalculaSalario;
import com.ufes.pss.gestaofuncionarios.view.CalcularSalarioView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CalcularSalarioPresenter {

    private CalcularSalarioView view;
    private final DefaultTableModel tmFuncionarios;
    private SistemaDeLogs logs;

    public CalcularSalarioPresenter(PrincipalPresenter principal, FuncionarioCollection funcionarios, SistemaDeLogs logs) {
        view = new CalcularSalarioView();
        principal.getView().getDesktop().add(view);
        this.logs = logs;

        tmFuncionarios = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Funcionario", "Data", "Salário Base (R$)", "Bônus (R$)", "Salário (R$)"}
        ) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        view.getTblFuncionarios().setModel(tmFuncionarios);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            fechar();
        });

        view.getBtnCalcular().addActionListener((ActionEvent ae) -> {
            if (!view.getTxtDataCalculo().getText().equals("")) {
                calcular(view.getTxtDataCalculo().getText(), funcionarios);
            } else {
                JOptionPane.showMessageDialog(view, "Insira uma data válida para cálculo!");
            }
        });

        view.getBtnNaoCalculados().addActionListener((ActionEvent ae) -> {
            listarNaoCalculados(funcionarios);
        });

        view.getBtnBuscar().addActionListener((ActionEvent ae) -> {
            if (!view.getTxtDataBusca().getText().equals("")) {
                buscarData(view.getTxtDataBusca().getText(), funcionarios);
            } else {
                JOptionPane.showMessageDialog(view, "Insira uma data válida para busca!");
            }
        });

        view.setVisible(true);
    }

    private void listarNaoCalculados(FuncionarioCollection funcionarios) {
        clearTable();

        for (Funcionario f : funcionarios.getFuncionarios()) {
            if (f.getSalario() == f.getSalarioComBonus()) {
                tmFuncionarios.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    "Não calculado",
                    String.format("%.2f", f.getSalario()).replace(".", ","),
                    "Não calculado",
                    "Não calculado"
                });

            }
        }

    }

    private void fechar() {
        view.dispose();
    }

    private void buscarData(String dataBusca, FuncionarioCollection funcionarios) {
        clearTable();

        LocalDate dt = LocalDate.parse(dataBusca, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Funcionario f : funcionarios.getFuncionarios()) {
            for (BonusHistorico b : f.getHistoricoBonus().getHistorico()) {
                if (b.getData() != null && b.getData().equals(dt)) {
                    tmFuncionarios.addRow(new Object[]{
                        f.getId(),
                        f.getNome(),
                        b.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        String.format("%.2f", f.getSalario()).replace(".", ","),
                        String.format("%.2f", (f.getSalarioComBonus() - f.getSalario())).replace(".", ","),
                        String.format("%.2f", f.getSalarioComBonus()).replace(".", ",")
                    });
                    break;
                }
            }
        }

    }

    /*Calcula os salários com bônus de todos os funcionarios listados na tabela
    seja eles os não calculados ou os calculados em determinada data 
     */
    private void calcular(String dataCalculo, FuncionarioCollection funcionarios) {
        int row = view.getTblFuncionarios().getRowCount();
        if (row > 0) {
            ArrayList<String> nomes = new ArrayList<>();
            for (int i = 0; i < row; i++) {

                int id = Integer.parseInt(view.getTblFuncionarios().getValueAt(i, 0).toString());

                Funcionario f = funcionarios.findById(id);

                nomes.add(f.getNome());

                CalculaSalario.CalcularSalario(f, dataCalculo);

            }
            logs.getLogger().logCalculaSalario(nomes);
            clearTable();
        } else {
            String msg = "Nenhum funcionário para calcular!";
            logs.getLogger().logFalha("Cálculo de Salário", msg);
            throw new RuntimeException(msg);
        }

    }

    private void clearTable() {
        tmFuncionarios.setRowCount(0);
        view.getTblFuncionarios().setModel(tmFuncionarios);
    }
}
