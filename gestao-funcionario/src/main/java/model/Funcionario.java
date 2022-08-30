/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valmir Aguiar
 */
public class Funcionario {

    private String nome;
    private double salarioBase;
    private double salario;
    private double distanciaDoTrabalho;
    private List<Bonus> bonus;
    private int numeroDeFilhos;
    private int membrosDaFamilia;
    private int numeroDeFaltas;
    private int horasExtras;
    

    public Funcionario(
            String nome, 
            double salarioBase, 
            double distanciaDoTrabalho, 
            int numeroDeFilhos, 
            int membrosDaFamilia
    ) {
        this.nome = nome;
        this.salarioBase = salarioBase;
        this.distanciaDoTrabalho = distanciaDoTrabalho;
        this.salario = salarioBase;
        this.numeroDeFilhos = numeroDeFilhos;
        this.membrosDaFamilia = membrosDaFamilia;        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getDistanciaDoTrabalho() {
        return distanciaDoTrabalho;
    }

    public void setDistanciaDoTrabalho(double distanciaDoTrabalho) {
        this.distanciaDoTrabalho = distanciaDoTrabalho;
    }

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void addBonus(Bonus bonus) {
        if(this.bonus == null)
            this.bonus =  new ArrayList<>();
        
        this.bonus.add(bonus);
        calculaSalarioComBonus();
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
    
    public void removerTodosBonus(){
        this.bonus.clear();
    }
    
    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getNumeroDeFilhos() {
        return numeroDeFilhos;
    }

    public void setNumeroDeFilhos(int numeroDeFilhos) {
        this.numeroDeFilhos = numeroDeFilhos;
    }

    public int getMembrosDaFamilia() {
        return membrosDaFamilia;
    }

    public void setMembrosDaFamilia(int membrosDaFamilia) {
        this.membrosDaFamilia = membrosDaFamilia;
    }

    public int getNumeroDeFaltas() {
        return numeroDeFaltas;
    }

    public void setNumeroDeFaltas(int numeroDeFaltas) {
        this.numeroDeFaltas = numeroDeFaltas;
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }
    
    public void calculaSalarioComBonus() {
        for(Bonus b : this.bonus) {
            this.salario += b.getValor();
        }
    }

    @Override
    public String toString() {
        return "Funcionario{" + 
                "\nnome=" + nome + 
                ", \nsalarioBase=" + salarioBase + 
                ", \nsalario=" + salario + 
                ", \ndistanciaDoTrabalho=" + distanciaDoTrabalho + 
                ", \nbonus=\n" + bonus + "}";
    }
}
