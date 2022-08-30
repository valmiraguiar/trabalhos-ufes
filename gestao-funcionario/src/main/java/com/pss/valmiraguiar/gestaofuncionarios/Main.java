/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.pss.valmiraguiar.gestaofuncionarios;

import bonus.CalculadoraBonusBom;
import bonus.CalculadoraBonusOtimo;
import bonus.CalculadoraDistanciaDoTrabalho;
import interfaces.ICalculadoraBonus;
import model.Funcionario;

/**
 *
 * @author Valmir Aguiar
 */
public class Main {

    public static void main(String[] args) {
        Funcionario f = new Funcionario("Valmir Aguiar", 4000.0, 22.0, 0, 2);
        Funcionario f2 = new Funcionario("José", 5000, 68, 3, 2);
        
        ICalculadoraBonus calculadora = new CalculadoraDistanciaDoTrabalho();

        calculadora.calculaBonus(f);

        calculadora = new CalculadoraBonusBom();
        calculadora.calculaBonus(f);

        calculadora = new CalculadoraBonusOtimo();
        calculadora.calculaBonus(f);
        
        System.out.println(f.toString());
    }
}
