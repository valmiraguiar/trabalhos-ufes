package Model;

import java.util.ArrayList;

public class FuncionarioDAO {

    private ArrayList<Funcionario> funcionarios;

    public FuncionarioDAO() {
        this.funcionarios = new ArrayList<>();
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Funcionario buscarFuncionarioId(int id) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        return null;
    }

    public void removerFuncionario(Funcionario f) {
        funcionarios.remove(f);
    }

    public FuncionarioDAO buscarFuncionariosNome(String nome) {
        FuncionarioDAO busca = new FuncionarioDAO();

        for (Funcionario f : funcionarios) {
            if (f.getNome().contains(nome)) {
                busca.addFuncionario(f);
            }
        }

        return busca;
    }


    @Override
    public String toString() {
        String retorno = "";
        for (Funcionario f : funcionarios) {
            retorno += f.toString();
        }
        return retorno;
    }

}
