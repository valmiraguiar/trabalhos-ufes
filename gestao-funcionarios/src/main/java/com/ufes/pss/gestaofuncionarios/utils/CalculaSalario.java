package com.ufes.pss.gestaofuncionarios.utils;

import com.ufes.pss.gestaofuncionarios.model.Funcionario;
import com.ufes.pss.gestaofuncionarios.model.Bonus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculaSalario {
    
    public static void CalcularSalario(Funcionario funcionario, String dataCalculo) {
        double salarioCalculado = funcionario.getSalario();

        for (Bonus b : funcionario.getBonusRecebidos()) {
            if (b.getData() != null) {
                if (b.getData().isBefore(LocalDate.parse(dataCalculo, DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                    salarioCalculado += funcionario.getSalario() * b.getValor();
                    b.setData(dataCalculo);

                    funcionario.getHistoricoBonus().addBonusHistorico(b, funcionario.getCargo(), funcionario.getSalario());
                } else {
                    throw new RuntimeException("Bônus já foram calculados em uma data posterior a indicada!");
                }
            } else {
                salarioCalculado += funcionario.getSalario() * b.getValor();
                b.setData(dataCalculo);

                funcionario.getHistoricoBonus().addBonusHistorico(b, funcionario.getCargo(), funcionario.getSalario());
            }

        }

        funcionario.setSalarioComBonus(salarioCalculado);

    }

}