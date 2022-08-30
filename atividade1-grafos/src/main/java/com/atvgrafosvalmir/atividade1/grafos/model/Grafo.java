/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.atvgrafosvalmir.atividade1.grafos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valmir Aguiar
 */
public class Grafo {

    private List<List<Integer>> matrizAdj;
    private String txt;
    private int vertices = 0;
    private int arestas = 0;
    private int digrafo = 0;
    private int valorado = 0;
    private List<Integer> listTxt = new ArrayList<>();
    private List<Integer> vetorValor;
    private List<Integer> vetorPoint;
    private List<Integer> vetorAdj;

    public Grafo(String txt) {
        this.txt = txt;
        processarEntradaInserirMatrizAdj();
    }

    public void printInfoGrafo() {
        System.out.println("========== MatrizAdj ==========");
        for (int i = 0; i < vertices; i++) {
            System.out.println(matrizAdj.get(i).toString());
        }

        System.out.println("=============");
        System.out.println("Vertices: " + this.vertices);
        System.out.println("Arestas: " + this.arestas);
        System.out.println("Digrafo: " + this.digrafo);
        System.out.println("Valorado: " + this.valorado);
        System.out.println("=============");

        // DADOS LIDOS DO TXT
        System.out.println("==================== LISTA LIDA DO TEXTO ============");
        System.out.println(listTxt.toString());

        System.out.println("=============\n");

        System.out.println("==================== VETOR ADJ ============");
        System.out.println(vetorAdj.toString());
        System.out.println("==================== VETOR POINT ============");
        System.out.println(vetorPoint.toString());
        if (vetorValor.size() > 0) {
            System.out.println("==================== VETOR VALORES ============");
            System.out.println(vetorValor.toString());
        }
    }

    private void processarEntradaInserirMatrizAdj() {
        String aux = this.txt.replaceAll(" ", "\n");
        String[] text = aux.split("\n");

        for (String s : text) {
            listTxt.add(Integer.parseInt(s.replace("\r", "")));
        }

        this.vertices = listTxt.get(0);
        this.arestas = listTxt.get(1);
        this.digrafo = listTxt.get(2);
        this.valorado = listTxt.get(3);

        if (this.digrafo == 0)
            inserirNaoDigrafo(this.valorado);
        else
            inserirDigrafo(this.valorado);

        criarFS(this.valorado);
    }

    public void printRetornoAtividade() {
        for (Integer n : this.vetorAdj) {
            System.out.print(n + " ");
        }
        System.out.println();
        for (Integer n : this.vetorPoint) {
            System.out.print(n + " ");
        }
        System.out.println();
        if (this.vetorValor.size() > 0) {
            for (Integer n : this.vetorValor) {
                System.out.print(n + " ");
            }
        }
    }

    public List<List<Integer>> iniciarMatriz() {
        List<List<Integer>> matriz = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            List<Integer> l = new ArrayList<>();
            for (int j = 0; j < vertices; j++) {
                l.add(0);
            }
            matriz.add(l);
        }
        return matriz;
    }

    public void inserirNaoDigrafo(int valorado) {
        List<List<Integer>> matriz = iniciarMatriz();

        if (valorado == 0) {
            for (int i = 4; i <= ((arestas * 2) + 2); i++, i++) {
                int line = listTxt.get(i) - 1;
                int column = listTxt.get(i + 1) - 1;
                List<Integer> listaAux = matriz.get(line);
                listaAux.set(column, 1);
                matriz.set(line, listaAux);
                listaAux = matriz.get(column);
                listaAux.set(line, 1);
                matriz.set(column, listaAux);
            }
        }

        if (valorado == 1) {
            for (int i = 4; i <= ((arestas * 3) + 3); i++, i++, i++) {
                int line = listTxt.get(i) - 1;
                int column = listTxt.get(i + 1) - 1;
                int value = listTxt.get(i + 2);
                List<Integer> listaAux = matriz.get(line);
                listaAux.set(column, value);
                matriz.set(line, listaAux);
                listaAux = matriz.get(column);
                listaAux.set(line, value);
                matriz.set(column, listaAux);
            }
        }

        this.matrizAdj = matriz;
    }

    public void inserirDigrafo(int valorado) {
        List<List<Integer>> matriz = iniciarMatriz();

        if (valorado == 0) {
            for (int i = 4; i <= ((arestas * 2) + 2); i++, i++) {
                int line = listTxt.get(i) - 1; //recupera da lista lida do arquivo
                int column = listTxt.get(i + 1) - 1;

                List<Integer> listaAux = matriz.get(line);

                listaAux.set(column, 1);

                matriz.set(line, listaAux);
            }
        }

        if (valorado == 1) {
            for (int i = 4; i <= ((arestas * 3) + 3); i++, i++, i++) {
                int line = listTxt.get(i) - 1;
                int column = listTxt.get(i + 1) - 1;
                int value = listTxt.get(i + 2);
                List<Integer> listaAux = matriz.get(line);
                listaAux.set(column, value);
                matriz.set(line, listaAux);
            }
        }

        this.matrizAdj = matriz;
    }

    public void criarFS(int valorado) {
        this.vetorPoint = new ArrayList<>();
        this.vetorAdj = new ArrayList<>();

        vetorPoint.add(1); //inicia com 1

        if (valorado == 0) {
            for (List<Integer> list : this.matrizAdj) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != 0) {
                        vetorAdj.add(i + 1);
                    }
                }
                vetorPoint.add(vetorAdj.size() + 1);
            }
        } else {
            this.vetorValor = new ArrayList<>();
            for (List<Integer> list : this.matrizAdj) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != 0) {
                        vetorAdj.add(i + 1);
                        vetorValor.add(list.get(i));
                    }
                }
                vetorPoint.add(vetorAdj.size() + 1);
            }
        }
    }
}
