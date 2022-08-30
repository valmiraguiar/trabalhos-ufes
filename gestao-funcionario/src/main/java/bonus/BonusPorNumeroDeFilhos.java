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
public class BonusPorNumeroDeFilhos implements ICalculadoraBonus {

    @Override
    public void calculaBonus(Funcionario funcionario) {
        double bonus = (funcionario.getSalarioBase() * (funcionario.getNumeroDeFilhos() / 100)) - funcionario.getSalarioBase();
        funcionario.addBonus(new Bonus("Bonus por filho", bonus));
    }

}
