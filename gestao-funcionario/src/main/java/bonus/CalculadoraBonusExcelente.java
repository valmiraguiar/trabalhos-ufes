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
public class CalculadoraBonusExcelente implements ICalculadoraBonus{

    //Bonus de 20%
    @Override
    public void calculaBonus(Funcionario funcionario) {
        funcionario.addBonus(new Bonus("EXCELENTE", (funcionario.getSalarioBase() * 1.2) - funcionario.getSalarioBase()));
    }    
}
