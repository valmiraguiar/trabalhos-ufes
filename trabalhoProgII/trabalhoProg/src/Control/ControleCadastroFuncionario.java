package Control;

import Model.Dependente;
import Model.DependenteDAO;
import Model.Funcionario;
import Model.FuncionarioDAO;

import View.TelaCadastroFuncionario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControleCadastroFuncionario {

    private TelaCadastroFuncionario telaCadastroFuncionario; 
    private DependenteDAO dependentes;
    private FuncionarioDAO funcionarios;

    public ControleCadastroFuncionario(FuncionarioDAO funcionarios) {
        telaCadastroFuncionario = new TelaCadastroFuncionario();
        this.funcionarios = funcionarios;
        dependentes = new DependenteDAO();

        telaCadastroFuncionario.getBtnSalvarFuncionario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSalvarFuncionarioActionPerformed(e);
            }
        }
        );

        telaCadastroFuncionario.getBtnAddDependente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddDependentesActionPerformed(e);
            }
        });

        telaCadastroFuncionario.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaCadastroFuncionario.dispose();
            }
        });

    }

    private void limparCamposDependentes() {
        telaCadastroFuncionario.getTxtNomeDependente().setText("");
        telaCadastroFuncionario.getTxtParentesco().setText("");
    }

    private void limparCamposFuncionario() {
        limparCamposDependentes();
        telaCadastroFuncionario.getTxtaDependentes().setText("");
        telaCadastroFuncionario.getTxtNomeFuncionario().setText("");
        telaCadastroFuncionario.getTxtEndereco().setText("");
        telaCadastroFuncionario.getTxtDepartamento().setText("");
        telaCadastroFuncionario.getTxtCargo().setText("");
        telaCadastroFuncionario.getTxtDataInicio().setText("");
        telaCadastroFuncionario.getTxtSalario().setText("");
    }

    private void btnAddDependentesActionPerformed(ActionEvent e) {
        Dependente novo = new Dependente();
        novo.setNome(telaCadastroFuncionario.getTxtNomeDependente().getText());
        novo.setParentesco(telaCadastroFuncionario.getTxtParentesco().getText());

        if (telaCadastroFuncionario.getTxtNomeDependente().getText().equals("") || telaCadastroFuncionario.getTxtParentesco().getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campos obrigatórios de Dependentes não preenchidos");
        } else {
            dependentes.addDependente(novo);
            telaCadastroFuncionario.getTxtaDependentes().setText(dependentes.toString());
            limparCamposDependentes();
        }

    }

    private void btnSalvarFuncionarioActionPerformed(ActionEvent e) {

        try {
            Funcionario novo = new Funcionario();
            novo.setNome(telaCadastroFuncionario.getTxtNomeFuncionario().getText());
            novo.setEndereco(telaCadastroFuncionario.getTxtEndereco().getText());
            novo.setCargo(telaCadastroFuncionario.getTxtCargo().getText());
            novo.setDepartamento(telaCadastroFuncionario.getTxtDepartamento().getText());
            novo.setDataInicio(telaCadastroFuncionario.getTxtDataInicio().getText());
            novo.setDependentes(dependentes);
            novo.setSalario(Double.parseDouble(telaCadastroFuncionario.getTxtSalario().getText().replace(',', '.')));

            if (telaCadastroFuncionario.getTxtNomeFuncionario().getText().equals("")
                    || telaCadastroFuncionario.getTxtEndereco().getText().equals("")
                    || telaCadastroFuncionario.getTxtCargo().getText().equals("")
                    || telaCadastroFuncionario.getTxtSalario().getText().equals("")
                    || telaCadastroFuncionario.getTxtDepartamento().getText().equals("")
                    || telaCadastroFuncionario.getTxtDataInicio().getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campos obrigatórios de Funcionário não preenchidos");
            } else {
                novo.setId(GeradorId.gerarId());
                funcionarios.addFuncionario(novo);
                limparCamposFuncionario();
                dependentes = new DependenteDAO();
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Salário Inválido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void run() {
        telaCadastroFuncionario.setVisible(true);
        telaCadastroFuncionario.setLocationRelativeTo(null);
    }

}
