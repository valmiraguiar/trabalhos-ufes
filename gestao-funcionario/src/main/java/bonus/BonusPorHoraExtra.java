/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bonus;

import interfaces.ICalculadoraBonus;
import model.Bonus;
import model.Funcionario;

/**
 *
 * @author Valmir Aguiar
 */
public class BonusPorHoraExtra implements ICalculadoraBonus {

    @Override
    public void calculaBonus(Funcionario funcionario) {
        if (funcionario.getHorasExtras() <= 8) {
            funcionario.addBonus(new Bonus("Até 8 Horas Extras", (funcionario.getSalarioBase() * 1.005) - funcionario.getSalarioBase()));
        } else if (funcionario.getHorasExtras() <= 16) {
            funcionario.addBonus(new Bonus("Até 16 Horas Extras", (funcionario.getSalarioBase() * 1.01) - funcionario.getSalarioBase()));
        } else if (funcionario.getHorasExtras() <= 24) {
            funcionario.addBonus(new Bonus("Até 24 Horas Extras", (funcionario.getSalarioBase() * 1.03) - funcionario.getSalarioBase()));
        } else if (funcionario.getHorasExtras() > 24) {
            funcionario.addBonus(new Bonus("Mais que 24 Horas Extras", (funcionario.getSalarioBase() * 1.05) - funcionario.getSalarioBase()));
        }
    }

}
