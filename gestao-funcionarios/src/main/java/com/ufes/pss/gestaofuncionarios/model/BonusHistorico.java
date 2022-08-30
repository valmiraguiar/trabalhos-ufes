package com.ufes.pss.gestaofuncionarios.model;

public class BonusHistorico extends Bonus {

    private String cargo;
    private double salarioBase;

    public BonusHistorico(String nome, double valor, String cargo, double salarioBase) {
        super(nome, valor);
        this.setCargo(cargo);
        this.setSalarioBase(salarioBase);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

}
