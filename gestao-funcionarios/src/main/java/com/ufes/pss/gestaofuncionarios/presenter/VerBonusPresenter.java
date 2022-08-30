package com.ufes.pss.gestaofuncionarios.presenter;

import com.ufes.pss.gestaofuncionarios.factory.SistemaDeLogs;
import com.ufes.pss.gestaofuncionarios.model.BonusHistorico;
import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import com.ufes.pss.gestaofuncionarios.view.VerBonusView;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

public class VerBonusPresenter {
    
    private VerBonusView view;
    private DefaultTableModel tmBonus;
    private SistemaDeLogs logs;
    
    public VerBonusPresenter(PrincipalPresenter principal, Funcionario funcionario, SistemaDeLogs logs) {
        this.view = new VerBonusView();
        principal.getView().getDesktop().add(view);
        this.logs = logs;
        
        logs.getLogger().logConsultaBonus(funcionario.getNome());
        
        tmBonus = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Data de Cálculo", "Cargo", "Tipo de Bônus", "Valor"}
        ) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        this.view.getTblBonus().setModel(tmBonus);
        preenche(funcionario);
        
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            fechar();
        });
        
        this.view.setVisible(true);
    }
    
    private void preenche(Funcionario f) {
        
        view.getLblNome().setText(f.getNome());
        view.getLblCargo().setText(f.getCargo());
        
        for (BonusHistorico bh : f.getHistoricoBonus().getHistorico()) {
            String data;
            
            if (bh.getData() == null) {
                data = "-";
            } else {
                data = bh.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            tmBonus.addRow(new Object[]{
                data,
                bh.getCargo(),
                bh.getNome(),
                String.format("R$%.2f", bh.getValor() * bh.getSalarioBase()).replace(".", ",")
            });
        }
    }
    
    private void fechar() {
        view.dispose();
    }
    
}
