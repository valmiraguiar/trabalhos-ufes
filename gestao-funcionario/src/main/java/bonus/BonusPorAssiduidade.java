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
public class BonusPorAssiduidade implements ICalculadoraBonus {

    @Override
    public void calculaBonus(Funcionario funcionario) {
        switch (funcionario.getNumeroDeFaltas()) {
            case 0:
                funcionario.addBonus(new Bonus("0 Faltas", (funcionario.getSalarioBase() * 1.03) - funcionario.getSalarioBase()));
                break;
            case 1:
                funcionario.addBonus(new Bonus("0 Faltas", (funcionario.getSalarioBase() * 1.01) - funcionario.getSalarioBase()));
                break;
            case 2:
                funcionario.addBonus(new Bonus("0 Faltas", (funcionario.getSalarioBase() * 1.005) - funcionario.getSalarioBase()));
                break;
            default:
                break;
        }
    }
}
