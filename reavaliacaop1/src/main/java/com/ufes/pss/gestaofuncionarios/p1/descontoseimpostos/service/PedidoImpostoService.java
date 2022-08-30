package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.service;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.IMetodoImposto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.ImpostoICMS;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.chain.ImpostoISS;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Imposto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;

import java.util.ArrayList;

public class PedidoImpostoService {
    private ArrayList<IMetodoImposto> metodosImposto;

    public PedidoImpostoService() {
        this.metodosImposto = new ArrayList<>();
        this.metodosImposto.add(new ImpostoISS());
        this.metodosImposto.add(new ImpostoICMS());
    }

    public void processarImposto(Pedido pedido) {
        for(IMetodoImposto metodoImposto : metodosImposto) {
            double valor = metodoImposto.calcularImposto(pedido);
            if(valor != 0) {
                pedido.addImposto(new Imposto(metodoImposto.getTipoImposto(), valor));
            }
        }
    }
}
