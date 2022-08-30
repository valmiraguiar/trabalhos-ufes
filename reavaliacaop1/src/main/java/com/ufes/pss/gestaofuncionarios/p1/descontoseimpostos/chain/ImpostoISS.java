package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

public class ImpostoISS implements IMetodoImposto{
    @Override
    public double calcularImposto(Pedido pedido) {
        return pedido.getValorTotal() * 0.02;
    }

    @Override
    public String getTipoImposto() {
        return "Imposto ISS";
    }
}
