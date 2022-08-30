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
public class CalculadoraBonusBom implements ICalculadoraBonus{

    //Bonus de 5%
    @Override
    public void calculaBonus(Funcionario funcionario) {
        funcionario.addBonus(new Bonus("BOM", (funcionario.getSalarioBase() * 1.05) - funcionario.getSalarioBase()));
    }    
}
