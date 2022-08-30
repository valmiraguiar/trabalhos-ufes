package com.ufes.pss.gestaofuncionarios.presenter;

import com.ufes.pss.gestaofuncionarios.factory.SistemaDeLogs;
import com.ufes.pss.gestaofuncionarios.view.SistemaDeLogsView;
import java.awt.event.ActionEvent;

public class SistemaDeLogsPresenter {

    private SistemaDeLogsView view;
    private SistemaDeLogs logs;

    public SistemaDeLogsPresenter(PrincipalPresenter principal, SistemaDeLogs logs) {
        view = new SistemaDeLogsView();
        principal.getView().getDesktop().add(view);

        if (logs.getTipo().toLowerCase().equals("txt")) {
            view.getRbtTXT().setSelected(true);
        } else if (logs.getTipo().toLowerCase().equals("json")) {
            view.getRbtJSON().setSelected(true);
        }

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            if (view.getRbtTXT().isSelected()) {
                setTXT(principal, logs);
            } else if (view.getRbtJSON().isSelected()) {
                setJSON(principal, logs);
            } else {
                view.dispose();
            }
        });

        view.setVisible(true);
    }

    private void setJSON(PrincipalPresenter p, SistemaDeLogs logs) {
        logs.setTipo("JSON");
        p.updateTipoLog();
        view.dispose();
    }

    private void setTXT(PrincipalPresenter p, SistemaDeLogs logs) {
        logs.setTipo("TXT");
        p.updateTipoLog();
        view.dispose();
    }

}
