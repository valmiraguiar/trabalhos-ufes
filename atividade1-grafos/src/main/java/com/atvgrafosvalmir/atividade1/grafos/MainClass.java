package com.atvgrafosvalmir.atividade1.grafos;


import com.atvgrafosvalmir.atividade1.grafos.model.Grafo;
import com.atvgrafosvalmir.atividade1.grafos.utils.FilesUtils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Valmir Aguiar
 */
public class MainClass {

    public static void main(String[] args) {
        //NOME DO ARQUIVO EXEMPLO: entrada1.txt
        String txt = new FilesUtils().lerArquivo("C:/Users/Valmir Aguiar/Documents/ufes/algoritmos-grafos/atividade1/atividade1-grafos/entrada3.txt"); // caminho do arquivo

        Grafo grafo = new Grafo(txt);
        grafo.printRetornoAtividade(); //Print da forma pedida na atividade
        //grafo.printInfoGrafo(); //Print de todas as informações
    }
}
