package com.ufes.pss.gestaofuncionarios.collection;

import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class FuncionarioCollection {

    private ArrayList<Funcionario> funcionarios;

    public FuncionarioCollection() {
        funcionarios = new ArrayList<>();
    }

    public void addFuncionario(Funcionario funcionario) throws RuntimeException {
        if (funcionarios.contains(funcionario)) {            
            throw new RuntimeException("Funcionário já cadastrado!");
        } else if (funcionario != null) {
            this.funcionarios.add(funcionario);
        } else {
            throw new RuntimeException("Forneça uma instância válida de funcionário");
        }
    }

    public void atualizarFuncionario(Funcionario antigo, Funcionario novo) throws RuntimeException {
        if (funcionarios.contains(novo)) {
            throw new RuntimeException("Funcionário já cadastrado");
        } else if (novo != null) {
            for (Funcionario f : funcionarios) {
                if (f.equals(antigo)) {
                    f.setNome(novo.getNome());
                    f.setCargo(novo.getCargo());
                    f.setIdade(novo.getIdade());
                    f.setSalario(novo.getSalario());
                    f.setBonusRecebidos(novo.getBonusRecebidos());
                }
            }
        }
    }

    public void atualizarFuncionarioById(int id, String nome, int idade, double salario, String cargo, int numFaltas, LocalDate admissao,
            boolean funcionarioDoMes, String formacao) throws RuntimeException {
        Funcionario f = this.findById(id);
        if (f != null) {
            f.setNome(nome);
            f.setIdade(idade);
            f.setSalario(salario);
            f.setCargo(cargo);
            f.setNumFaltas(numFaltas);
            f.setAdmissao(admissao);
            f.setFuncionarioDoMes(funcionarioDoMes);
            f.setFormacao(formacao);
        } else {
            throw new RuntimeException("Erro ao atualizar funcionário");
        }
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Funcionario findById(int id) {
        for (Funcionario f : this.getFuncionarios()) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public void ordenar() {
        Collections.sort(funcionarios);
    }

}
