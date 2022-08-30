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
public class CalculadoraDistanciaDoTrabalho implements ICalculadoraBonus{

    @Override
    public void calculaBonus(Funcionario funcionario) {
        if(funcionario.getDistanciaDoTrabalho() <= 50.0){
            funcionario.addBonus(new Bonus("DistânciaAté50Km", 50.0));
        } else if(funcionario.getDistanciaDoTrabalho() <= 100.0) {
            funcionario.addBonus(new Bonus("DistânciaAté100Km", 90.0));
        } else if(funcionario.getDistanciaDoTrabalho() > 100.0) {
            funcionario.addBonus(new Bonus("DistânciaMaisDe100Km", 150.0));
        }
    }
}
