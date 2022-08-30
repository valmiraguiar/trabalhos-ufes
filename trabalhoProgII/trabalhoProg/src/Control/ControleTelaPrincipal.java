package Control;

import Model.FuncionarioDAO;
import View.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleTelaPrincipal {

    private TelaPrincipal telaPrincipal;
    private FuncionarioDAO funcionarios;

    public ControleTelaPrincipal() {
        this.telaPrincipal = new TelaPrincipal();
        this.funcionarios = new FuncionarioDAO();

        telaPrincipal.getBtnCadastrarFuncionario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntCadastrarFuncionarioActionPerformed(e);
            }
        });

        telaPrincipal.getBtnVerFuncionarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntVerFuncionariosActionPerformed(e);
            }
        });
        telaPrincipal.getBtnPesquisa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntPesquisaActionPerformed(e);
            }
        });

    }

    private void bntCadastrarFuncionarioActionPerformed(ActionEvent e) {
        ControleCadastroFuncionario cadastro = new ControleCadastroFuncionario(funcionarios);
        cadastro.run();
    }

    private void bntVerFuncionariosActionPerformed(ActionEvent e) {
        ControleVerFuncionarios verFuncionarios = new ControleVerFuncionarios(funcionarios);
        verFuncionarios.run();
    }

    private void bntPesquisaActionPerformed(ActionEvent e) {
        ControleEdicao edicao = new ControleEdicao(funcionarios);
        edicao.run();
    }

    public void run() {

        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setVisible(true);
    }
}
