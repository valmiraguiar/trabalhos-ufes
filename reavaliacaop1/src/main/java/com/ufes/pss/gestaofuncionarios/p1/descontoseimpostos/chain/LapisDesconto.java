package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.ItemPedido;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

public class LapisDesconto implements IMetodoDesconto{
    @Override
    public double calcularDesconto(Pedido pedido) {
        for(ItemPedido itemPedido : pedido.getItens()) {
            if(itemPedido.getProduto().getNome().equals("Lápis")) {
                return pedido.getValorTotal() * 0.005;
            }
        }
        return 0.0;
    }

    @Override
    public String getTipoDesconto() {
        return "Desconto Lápis";
    }
}
