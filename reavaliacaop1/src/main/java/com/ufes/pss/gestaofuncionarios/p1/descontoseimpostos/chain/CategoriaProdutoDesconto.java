package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.ItemPedido;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

public class CategoriaProdutoDesconto implements IMetodoDesconto {
    private String categoria = "";

    @Override
    public double calcularDesconto(Pedido pedido) {
        double valor = 0.0;
        for(ItemPedido itemPedido : pedido.getItens()) {
            if(itemPedido.getProduto().getCategoria().equals("Papelaria")) {
                valor += pedido.getValorTotal() * 0.01;
                categoria.concat(" (Papelaria)");
            }
        }
        return valor;
    }

    @Override
    public String getTipoDesconto() {
        return "Desconto Categoria Produto"+categoria;
    }
}
