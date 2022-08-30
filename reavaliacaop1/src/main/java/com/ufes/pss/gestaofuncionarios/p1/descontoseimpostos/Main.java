/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos;

import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.ItemPedido;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Pedido;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model.Produto;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.service.PedidoDescontoService;
import com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.service.PedidoImpostoService;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Valmir Aguiar
 */
public class Main {

    public static void main(String[] args) {
        Produto lapis = new Produto("Lápis", 2.50, 12, "Papelaria");
        Produto caneta = new Produto("Caneta", 2.50, 4, "Papelaria");
        Produto bolaGude = new Produto("Bola de Gude", 2.50, 6, "Lazer");
        Produto pastaDente = new Produto("Pasta de Dente", 2.50, 3, "Higiene");

        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(lapis);
        produtos.add(caneta);
        produtos.add(bolaGude);
        produtos.add(pastaDente);

        ItemPedido lapisPedido = new ItemPedido(4, lapis);
        ItemPedido bolaGudePedido = new ItemPedido(3, bolaGude);

        Pedido pedidoFeito = new Pedido("Valmir Aguiar", LocalDate.now());

        pedidoFeito.addItem(lapisPedido);
        pedidoFeito.addItem(bolaGudePedido);

        PedidoDescontoService descontoService = new PedidoDescontoService();
        PedidoImpostoService impostoService = new PedidoImpostoService();

        descontoService.processarDesconto(pedidoFeito);
        impostoService.processarImposto(pedidoFeito);

        System.out.println(pedidoFeito);
    }
}
