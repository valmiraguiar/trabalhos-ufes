package Control;

import Model.Funcionario;
import Model.FuncionarioDAO;
import View.TelaEdicao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControleEdicao {

    private TelaEdicao telaEdicao;
    FuncionarioDAO funcionarios;

    public ControleEdicao(FuncionarioDAO funcionarios) {
        telaEdicao = new TelaEdicao();
        this.funcionarios = funcionarios;

        telaEdicao.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaEdicao.dispose();
            }
        });

        telaEdicao.getBtnPesquisar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPesquisarActionPerformed(e);
            }
        });

        telaEdicao.getBtnExcluirFuncionario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnExcluirFuncionarioActionPerformed(e);
            }
        });

        telaEdicao.getBtnSalvarEdicao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSalvarEdicaoActionPerformed(e);
            }
        });
        
        telaEdicao.getBtnCarregarDados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCarregarDadosActionPerformed(e);
            }
        });

    }
    
    private void limparCampos() {
        telaEdicao.getTxtNomeFuncionario().setText("");
        telaEdicao.getTxtCargo().setText("");
        telaEdicao.getTxtNome().setText("");
        telaEdicao.getTxtIdEdit().setText("");
        telaEdicao.getTxtIdExcluir().setText("");
        telaEdicao.getTxtDataInicio().setText("");
        telaEdicao.getTxtDepartamento().setText("");
        telaEdicao.getTxtEndereco().setText("");
        telaEdicao.getTxtSalario().setText("");
        telaEdicao.getTxtaDadosFuncionario().setText("");
    }

    private void btnSalvarEdicaoActionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(telaEdicao.getTxtIdEdit().getText());
            Funcionario f = funcionarios.buscarFuncionarioId(id);

            if (!telaEdicao.getTxtNome().getText().equals("")) {
                f.setNome(telaEdicao.getTxtNome().getText());
            }

            if (!telaEdicao.getTxtCargo().getText().equals("")) {
                f.setCargo(telaEdicao.getTxtCargo().getText());
            }

            if (!telaEdicao.getTxtEndereco().getText().equals("")) {
                f.setEndereco(telaEdicao.getTxtEndereco().getText());
            }

            if (!telaEdicao.getTxtSalario().getText().equals("")) {
                f.setSalario(Double.parseDouble(telaEdicao.getTxtSalario().getText().replace(',', '.')));
            }

            if (!telaEdicao.getTxtDepartamento().getText().equals("")) {
                f.setDepartamento(telaEdicao.getTxtDepartamento().getText());
            }

            if (!telaEdicao.getTxtDataInicio().getText().equals("")) {
                f.setDataInicio(telaEdicao.getTxtDataInicio().getText());
            }

            JOptionPane.showMessageDialog(null, "Editado com sucesso!");
            limparCampos();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Salário inválido!");
        } catch (NullPointerException npe){
            JOptionPane.showMessageDialog(null, "ID Não encontrado");
        }

    }

    
    private void btnCarregarDadosActionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(telaEdicao.getTxtIdEdit().getText());
            Funcionario f = funcionarios.buscarFuncionarioId(id);
            if(f != null){
                telaEdicao.getTxtNome().setText(f.getNome());
                telaEdicao.getTxtEndereco().setText(f.getEndereco());
                telaEdicao.getTxtCargo().setText(f.getCargo());
                telaEdicao.getTxtDepartamento().setText(f.getDepartamento());
                telaEdicao.getTxtDataInicio().setText(f.getDataInicio());
                telaEdicao.getTxtSalario().setText(String.valueOf(f.getSalario()));
            }else{
                JOptionPane.showMessageDialog(null, "ID não encontrado");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void btnPesquisarActionPerformed(ActionEvent e) {
        if (telaEdicao.getTxtNomeFuncionario().getText().equals("") == false) {
            telaEdicao.getTxtaDadosFuncionario().setText(funcionarios.buscarFuncionariosNome(telaEdicao.getTxtNomeFuncionario().getText()).toString());
            if (funcionarios.buscarFuncionariosNome(telaEdicao.getTxtNomeFuncionario().getText()).toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Não encontrado!");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Digite um nome!");
        }

    }


    private void btnExcluirFuncionarioActionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(telaEdicao.getTxtIdExcluir().getText());
            Funcionario f = funcionarios.buscarFuncionarioId(id);
            funcionarios.removerFuncionario(f);

            telaEdicao.getTxtIdExcluir().setText("");

            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID não encontrado");
        }
    }

    public void run() {

        telaEdicao.setVisible(true);
        telaEdicao.setLocationRelativeTo(null);

    }

}
