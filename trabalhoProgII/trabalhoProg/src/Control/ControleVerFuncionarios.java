package Control;

import View.TelaVerFuncionarios;
import Model.FuncionarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleVerFuncionarios {

    private TelaVerFuncionarios telaVerFuncionarios;
    FuncionarioDAO funcionarios;

    public ControleVerFuncionarios(FuncionarioDAO funcionarios) {
        this.funcionarios = funcionarios;
        telaVerFuncionarios = new TelaVerFuncionarios();
        telaVerFuncionarios.getTxtaFuncionarios().setText(funcionarios.toString());
        
        telaVerFuncionarios.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaVerFuncionarios.dispose();
            }
        });
        
    }
   

    public void run() {

        telaVerFuncionarios.setVisible(true);
        telaVerFuncionarios.setLocationRelativeTo(null);

    }
}
