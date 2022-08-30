/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.ufes.pss.gestaofuncionarios.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

/**
 *
 * @author Valmir Aguiar
 */
public class SistemaDeLogsView extends javax.swing.JInternalFrame {

    /**
     * Creates new form SistemaDeLogsVIew
     */
    public SistemaDeLogsView() {
        initComponents();
        gpSistemaLogs.add(rbtTXT);
        gpSistemaLogs.add(rbtJSON);
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public void setBtnSalvar(JButton btnSalvar) {
        this.btnSalvar = btnSalvar;
    }

    public ButtonGroup getGpSistemaLogs() {
        return gpSistemaLogs;
    }

    public void setGpSistemaLogs(ButtonGroup gpSistemaLogs) {
        this.gpSistemaLogs = gpSistemaLogs;
    }

    public JRadioButton getRbtTXT() {
        return rbtTXT;
    }

    public void setRbtTXT(JRadioButton rbtTXT) {
        this.rbtTXT = rbtTXT;
    }

    public JRadioButton getRbtJSON() {
        return rbtJSON;
    }

    public void setRbtJSON(JRadioButton rbtJSON) {
        this.rbtJSON = rbtJSON;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gpSistemaLogs = new javax.swing.ButtonGroup();
        rbtJSON = new javax.swing.JRadioButton();
        rbtTXT = new javax.swing.JRadioButton();
        btnSalvar = new javax.swing.JButton();

        setTitle("Escolha um Sistema de Logs");

        rbtJSON.setText("JSON");

        rbtTXT.setText("TXT");

        btnSalvar.setText("Salvar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rbtJSON)
                        .addGap(83, 83, 83)
                        .addComponent(rbtTXT)
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addGap(113, 113, 113))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtJSON)
                    .addComponent(rbtTXT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup gpSistemaLogs;
    private javax.swing.JRadioButton rbtJSON;
    private javax.swing.JRadioButton rbtTXT;
    // End of variables declaration//GEN-END:variables
}
