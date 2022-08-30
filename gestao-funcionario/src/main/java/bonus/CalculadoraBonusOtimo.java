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
public class CalculadoraBonusOtimo implements ICalculadoraBonus{

    @Override
    public void calculaBonus(Funcionario funcionario) {
        funcionario.addBonus(new Bonus("ÓTIMO", (funcionario.getSalarioBase() * 1.1) - funcionario.getSalarioBase()));
    }    
}
