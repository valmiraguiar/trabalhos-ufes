package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

public interface IMetodoDesconto {
    public double calcularDesconto(Pedido pedido);
    public String getTipoDesconto();
}
