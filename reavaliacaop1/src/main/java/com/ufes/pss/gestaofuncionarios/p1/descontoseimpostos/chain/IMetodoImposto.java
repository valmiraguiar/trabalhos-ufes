package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

public interface IMetodoImposto {
    public double calcularImposto(Pedido pedido);
    public String getTipoImposto();
}
