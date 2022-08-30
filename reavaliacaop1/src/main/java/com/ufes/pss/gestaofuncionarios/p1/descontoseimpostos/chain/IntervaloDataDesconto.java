package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

import java.time.LocalDate;

public class IntervaloDataDesconto implements IMetodoDesconto{
    @Override
    public double calcularDesconto(Pedido pedido) {
        if(pedido.getData().isAfter(LocalDate.parse("2022-12-10")) &&
                pedido.getData().isBefore(LocalDate.parse("2022-12-25"))) {
            return pedido.getValorTotal() * 0.03;
        }
        return 0.0;
    }

    @Override
    public String getTipoDesconto() {
        return "Desconto Intervalo Data";
    }
}
