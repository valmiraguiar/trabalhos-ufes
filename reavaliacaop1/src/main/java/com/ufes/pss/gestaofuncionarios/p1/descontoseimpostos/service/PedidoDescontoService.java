package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.service;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.CategoriaProdutoDesconto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.IMetodoDesconto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.IntervaloDataDesconto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.LapisDesconto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Desconto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

import java.util.ArrayList;

public class PedidoDescontoService {
    private ArrayList<IMetodoDesconto> metodosDesconto;

    public PedidoDescontoService() {
        this.metodosDesconto = new ArrayList<>();
        this.metodosDesconto.add(new CategoriaProdutoDesconto());
        this.metodosDesconto.add(new IntervaloDataDesconto());
        this.metodosDesconto.add(new LapisDesconto());
    }

    public void processarDesconto(Pedido pedido) {
        for(IMetodoDesconto metodoDesconto: metodosDesconto) {
            double valor = metodoDesconto.calcularDesconto(pedido);
            if(valor != 0) {
                pedido.addDesconto(new Desconto(metodoDesconto.getTipoDesconto(), valor));
            }
        }
    }
}
