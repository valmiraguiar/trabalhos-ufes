package com.ufes.pss.gestaofuncionarios.presenter;

import com.ufes.pss.gestaofuncionarios.collection.FuncionarioCollection;
import com.ufes.pss.gestaofuncionarios.factory.SistemaDeLogs;
import com.ufes.pss.gestaofuncionarios.model.Bonus;
import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import com.ufes.pss.gestaofuncionarios.utils.AddBonusRecebidosCadastro;
import com.ufes.pss.gestaofuncionarios.view.ManterFuncionarioView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManterFuncionarioPresenter {

    private final ManterFuncionarioView view;
    private final FuncionarioCollection funcionarios;
    private final SistemaDeLogs logs;

    public ManterFuncionarioPresenter(PrincipalPresenter principal, FuncionarioCollection funcionarios, SistemaDeLogs logs) {
        this.funcionarios = funcionarios;
        this.logs = logs;

        view = new ManterFuncionarioView();
        principal.getView().getDesktop().add(view);

        view.getBtnExcluir().setEnabled(false);
        view.getBtnEditar().setEnabled(false);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            fechar();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            salvar(principal);
        });

        view.setVisible(true);

    }

    public ManterFuncionarioPresenter(PrincipalPresenter principal,
            FuncionarioCollection funcionarios, Funcionario f, SistemaDeLogs logs) {

        this.funcionarios = funcionarios;
        this.logs = logs;

        view = new ManterFuncionarioView();
        principal.getView().getDesktop().add(view);

        preparaVisualizacao(f);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            fechar();
        });

        view.getBtnEditar().addActionListener((ActionEvent ae) -> {
            preparaEdicao(f);
        });

        view.getBtnExcluir().addActionListener((ActionEvent ae) -> {
            excluir(principal, f);
        });

        view.setVisible(true);

    }

    private void preparaEdicao(Funcionario f) {
        view.getTxtNome().setEditable(true);
        view.getTxtAdmissao().setEditable(true);
        view.getTxtSalario().setEditable(true);
        view.getTxtIdade().setEditable(true);
        view.getTxtFaltas().setEditable(true);
        view.getCbxCargo().setEditable(false);
        view.getCbxBonus().setEditable(false);
        view.getCbxFormacao().setEditable(false);
        view.getCkbFuncionarioDoMes().setFocusable(true);

        view.getBtnSalvar().setEnabled(true);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnEditar().setEnabled(false);

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            editar(f);
        });

    }

    private void preparaVisualizacao(Funcionario f) {
        view.setTitle("Visualizacao");

        logs.getLogger().logCRUDFuncionario(f.getNome(), "Visualizado");
        try {
            view.getTxtNome().setText(f.getNome());
            view.getTxtAdmissao().setText(f.getAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            view.getTxtSalario().setText(Double.toString(f.getSalario()).replace(".", ","));
            view.getTxtIdade().setText(Integer.toString(f.getIdade()));
            view.getTxtFaltas().setText(Integer.toString(f.getNumFaltas()));
            view.getCbxCargo().setSelectedIndex(view.indexOfCargo(f.getCargo()));
            for (Bonus b : f.getBonusRecebidos()) {
                if (b.getNome().equals("Normal") || b.getNome().equals("Generoso")) {
                    view.getCbxBonus().setSelectedIndex(view.indexOfBonus(b.getNome()));
                }
            }
            view.getCbxFormacao().setSelectedIndex(view.indexOfFormacao(f.getFormacao()));
            view.getCkbFuncionarioDoMes().setSelected(f.isFuncionarioDoMes());

            view.getTxtNome().setEditable(false);
            view.getTxtAdmissao().setEditable(false);
            view.getTxtSalario().setEditable(false);
            view.getTxtIdade().setEditable(false);
            view.getTxtFaltas().setEditable(false);
            view.getCbxCargo().setEditable(false);
            view.getCbxBonus().setEditable(false);
            view.getCbxFormacao().setEditable(false);
            view.getCkbFuncionarioDoMes().setFocusable(false);

            view.getBtnSalvar().setEnabled(false);
        } catch (Exception e) {
            logs.getLogger().logFalha("Visualização", e.getMessage());
        }

    }

    private void fechar() {
        view.dispose();
    }

    private void editar(Funcionario f) {

        try {
            String nome = view.getTxtNome().getText();
            int idade = Integer.parseInt(view.getTxtIdade().getText());
            double salario = Double.parseDouble(view.getTxtSalario().getText().replace(",", "."));
            String cargo = view.getCbxCargo().getSelectedItem().toString();
            int numFaltas = Integer.parseInt(view.getTxtFaltas().getText());
            LocalDate admissao = LocalDate.parse(view.getTxtAdmissao().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            boolean funcionarioDoMes = view.getCkbFuncionarioDoMes().isSelected();
            String formacao = view.getCbxFormacao().getSelectedItem().toString();
            String bonusInicial = view.getCbxBonus().getSelectedItem().toString();

            funcionarios.atualizarFuncionarioById(f.getId(), nome, idade, salario, cargo, numFaltas, admissao, funcionarioDoMes, formacao);

            f.setBonusRecebidos(new ArrayList<>());

            AddBonusRecebidosCadastro.addBonusRecebidoCadastro(f, bonusInicial);

            logs.getLogger().logCRUDFuncionario(nome, "Editado");
        } catch (Exception e) {
            logs.getLogger().logFalha("Edição de funcionário", e.getMessage());
        }
        fechar();
    }

    private void salvar(PrincipalPresenter pp) {

        try {
            String nome = view.getTxtNome().getText();
            int idade = Integer.parseInt(view.getTxtIdade().getText());
            double salario = Double.parseDouble(view.getTxtSalario().getText().replace(",", "."));
            String cargo = view.getCbxCargo().getSelectedItem().toString();
            int numFaltas = Integer.parseInt(view.getTxtFaltas().getText());
            String admissao = view.getTxtAdmissao().getText();
            boolean funcionarioDoMes = view.getCkbFuncionarioDoMes().isSelected();
            String formacao = view.getCbxFormacao().getSelectedItem().toString();
            String bonusInicial = view.getCbxBonus().getSelectedItem().toString();
            int id = funcionarios.getFuncionarios().size() + 1;

            Funcionario f = new Funcionario(id, nome, idade, salario, cargo, numFaltas, admissao, funcionarioDoMes, formacao);

            AddBonusRecebidosCadastro.addBonusRecebidoCadastro(f, bonusInicial);

            funcionarios.addFuncionario(f);

            pp.updateNumFuncionarios();
            logs.getLogger().logCRUDFuncionario(nome, "Adicionado");
        } catch (Exception e) {
            logs.getLogger().logFalha("Adição de funcionário", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        fechar();

    }

    private void excluir(PrincipalPresenter pp, Funcionario f) {

        funcionarios.getFuncionarios().remove(f);
        pp.updateNumFuncionarios();
        logs.getLogger().logCRUDFuncionario(f.getNome(), "Excluido");
        fechar();
    }

}
