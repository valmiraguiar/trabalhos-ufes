package com.ufes.pss.gestaofuncionarios.model;

import com.ufes.pss.gestaofuncionarios.collection.HistoricoBonus;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Funcionario implements Serializable, Comparable<Funcionario> {

    private final int id;
    private String nome;
    private int idade;
    private double salario;
    private double salarioComBonus;
    private String cargo;
    private ArrayList<Bonus> bonusRecebidos;
    private int numFaltas;
    private boolean funcionarioDoMes;
    private LocalDate admissao;
    private HistoricoBonus historicoBonus;
    private String formacao;

    public Funcionario(int id, String nome, int idade, double salario, String cargo,
            int numFaltas, String admissao, boolean funcionarioDoMes, String formacao) {
        this.id = id;
        this.setNome(nome);
        this.setIdade(idade);
        this.setSalario(salario);
        this.setSalarioComBonus(this.getSalario());
        this.setCargo(cargo);
        this.setNumFaltas(numFaltas);
        this.setAdmissao(admissao);
        this.setFuncionarioDoMes(funcionarioDoMes);
        this.setFormacao(formacao);
        this.bonusRecebidos = new ArrayList<>();
        historicoBonus = new HistoricoBonus();
    }

    public void addBonus(Bonus bonusRecebido) {
        bonusRecebidos.add(bonusRecebido);
    }

    public LocalDate getAdmissao() {
        return admissao;
    }

    public void setAdmissao(LocalDate admissao) {
        this.admissao = admissao;
    }

    public void setAdmissao(String admissao) throws RuntimeException {
        try {
            this.admissao = LocalDate.parse(admissao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar data");
        }
    }

    public int getNumFaltas() {
        return numFaltas;
    }

    public void setNumFaltas(int numFaltas) {
        this.numFaltas = numFaltas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public ArrayList<Bonus> getBonusRecebidos() {
        return bonusRecebidos;
    }

    public void setBonusRecebidos(ArrayList<Bonus> bonusRecebidos) {
        this.bonusRecebidos = bonusRecebidos;
    }

    public boolean isFuncionarioDoMes() {
        return funcionarioDoMes;
    }

    public void setFuncionarioDoMes(boolean funcionarioDoMes) {
        this.funcionarioDoMes = funcionarioDoMes;
    }

    public double getSalarioComBonus() {
        return salarioComBonus;
    }

    public void setSalarioComBonus(double salarioComBonus) {
        this.salarioComBonus = salarioComBonus;
    }

    public HistoricoBonus getHistoricoBonus() {
        return historicoBonus;
    }

    public void setHistoricoBonus(HistoricoBonus historicoBonus) {
        this.historicoBonus = historicoBonus;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Funcionario outro) {
        return this.getNome().compareToIgnoreCase(outro.getNome());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + this.idade;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.salario) ^ (Double.doubleToLongBits(this.salario) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.cargo);
        hash = 29 * hash + Objects.hashCode(this.bonusRecebidos);
        hash = 29 * hash + this.numFaltas;
        hash = 29 * hash + Objects.hashCode(this.admissao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Funcionario other = (Funcionario) obj;
        if (this.idade != other.idade) {
            return false;
        }
        if (Double.doubleToLongBits(this.salario) != Double.doubleToLongBits(other.salario)) {
            return false;
        }
        if (this.numFaltas != other.numFaltas) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cargo, other.cargo)) {
            return false;
        }
        if (!Objects.equals(this.bonusRecebidos, other.bonusRecebidos)) {
            return false;
        }
        return Objects.equals(this.admissao, other.admissao);
    }

}
