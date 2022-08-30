package grafo

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class Solucao(val path: String) {

    val infinito: Int = 999999999

    private var distancias: MutableList<Int> = mutableListOf()

    private val matrizAdj: MutableList<MutableList<Int>> = mutableListOf()
    private val listaAdj: MutableList<MutableList<Int>> = mutableListOf()

    private var vertices: Int = 0
    private var arestas: Int = 0

    private var diametroMatriz = -1
    private var diametroLista = -1

    init {
        processText()
        var tempoMatrizInicio = System.currentTimeMillis()
        //for (i in 0..5) {
            calcularExcentricidadeMatrizAdj(this.matrizAdj)
        //}
        var tempoMatrizFim = System.currentTimeMillis()
        val tFinalMatriz = (((tempoMatrizFim - tempoMatrizInicio) / 1).toDouble()) / 1000

        val tempoListaInicio = System.currentTimeMillis()
        //for (i in 0..5) {
            calcularExcentricidadeListaAdj(this.listaAdj)
       // }
        val tempoListaFim = System.currentTimeMillis()
        val tFinalLista = (((tempoListaFim - tempoListaInicio) / 1).toDouble()) / 1000


        imprimir(tFinalMatriz, this.diametroMatriz, tFinalLista, this.diametroLista)
    }

    fun imprimir(tFinalMatriz: Double, diametroMatriz: Int, tFinalLista: Double, diametroLista: Int) {

        var txt = ""
        txt += "+++++++++ MATRIZ ADJ +++++++++\n\n"
        println("TEMPO USANDO MATRIZ ADJ -> "+"%8f".format(tFinalMatriz))
        txt +=  "TEMPO USANDO MATRIZ ADJ -> " + "%8f".format(tFinalMatriz)+"\n"
        println("DIAMETRO MATRIZ ADJ -> ${this.diametroMatriz}")
        txt +=  "DIAMETRO MATRIZ ADJ -> ${this.diametroMatriz}"+"\n"
        txt += "=========================================\n\n"
        txt += "+++++++++ LISTA ADJ +++++++++\n\n"
        println("TEMPO USANDO LISTA ADJ -> "+"%8f".format(tFinalLista))
        txt +=  "TEMPO USANDO LISTA ADJ -> " + "%8f".format(tFinalLista)+"\n"
        println("DIAMETRO LISTA ADJ -> ${this.diametroLista}")
        txt +=  "DIAMETRO LISTA ADJ -> ${this.diametroLista}"+"\n"
        txt += "=========================================\n\n"


        try {
            val titulo = "${path}-DIAMETRO CALCULADO.txt"
            val arquivo = File(titulo)
            arquivo.writeText(txt)//aqui você irá passar os dados
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun processText() {
        try {
            BufferedReader(FileReader(path)).use { br ->
                var line: String?
                br.readLine().also {
                    this.vertices = it.toInt()
                }
                br.readLine().also {
                    this.arestas = it.toInt()
                }

                iniciarmatrizAdj(this.vertices)
                iniciarListaAdj(this.vertices)

                for (i in 0 until this.arestas) {
                    br.readLine().also {
                        val linha = it.substringBefore("-").toInt()
                        val coluna = it.substringAfter("-").toInt()

                        this.matrizAdj[linha][coluna] = 1
                        this.listaAdj[linha].add(coluna)
                    }
                }
                println("Matriz -----------")
                for (i in 0 until this.matrizAdj.size) {
                    println(this.matrizAdj[i].toString())
                }

                println("Lista -----------")
                for (i in 0 until this.listaAdj.size) {
                    println(this.listaAdj[i].toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun iniciarmatrizAdj(vertices: Int) {
        for (i in 0 until vertices) {
            val list: MutableList<Int> = mutableListOf()

            for (j in 0 until vertices) {
                list.add(0)
            }
            this.matrizAdj.add(list)
        }
    }

    private fun iniciarListaAdj(vertices: Int) {
        for (i in 0 until vertices) {
            val list: MutableList<Int> = mutableListOf()
            this.listaAdj.add(list)
        }
    }

    private fun iniciarDistancias(vertices: Int) {
        for (i in 0 until vertices) {
            this.distancias.add(infinito)
        }
    }


    private fun existeArestaMatrizAdj(grafo: MutableList<MutableList<Int>>, i: Int, j: Int): Boolean {
        if (grafo[i][j] == 1) {
            return true
        }
        return false
    }

    private fun existeArestaListaAdj(grafo: MutableList<MutableList<Int>>, i: Int, j: Int): Boolean {
        if (grafo[i].contains(j))
            return true
        return false
    }

    private fun buscaEmLargura(grafo: MutableList<MutableList<Int>>, vertice: Int) {
        this.distancias.clear()
        iniciarDistancias(this.vertices)

        val fila: MutableList<Int> = mutableListOf()

        this.distancias[vertice] = 0
        fila.add(vertice)

        while (fila.isNotEmpty()) {
            val x = fila.first()
            fila.removeFirst()

            for (i in 0 until this.vertices) {

                if (existeArestaMatrizAdj(grafo, x, i) && this.distancias[i] == infinito) {
                    this.distancias[i] = distancias[x] + 1
                    fila.add(i)
                }

            }
        }

        /*println("DISTANCIAS")
        println(this.distancias.toString())*/
    }

    private fun buscaEmLarguraLista(grafo: MutableList<MutableList<Int>>, vertice: Int) {
        this.distancias.clear()
        iniciarDistancias(this.vertices)

        val fila: MutableList<Int> = mutableListOf()

        this.distancias[vertice] = 0
        fila.add(vertice)

        while (fila.isNotEmpty()) {
            val x = fila.first()
            fila.removeFirst()

            for (i in 0 until this.vertices) {

                if (existeArestaListaAdj(grafo, x, i) && this.distancias[i] == infinito) {
                    this.distancias[i] = distancias[x] + 1
                    fila.add(i)
                }

            }
        }

        /*println("DISTANCIAS")
        println(this.distancias.toString())*/
    }

    fun pegarMaiorDaLista(lista: MutableList<Int>): Int {
        lista.sortByDescending {
            it
        }

        return lista.first()
    }

    fun calcularExcentricidadeMatrizAdj(grafo: MutableList<MutableList<Int>>) {
        println("Calculando diametro usando matriz adj ...")
        val listaDistanciaMaximaPorVertice: MutableList<Int> = mutableListOf()

        for (i in 0 until this.vertices) {
            buscaEmLargura(grafo, i)
            listaDistanciaMaximaPorVertice.add(pegarMaiorDaLista(this.distancias))
        }

        /*println("DISTANCIAS")
        println(listaDistanciaMaximaPorVertice.toString())*/

        //buscar maior distancia
        var maiorDistancia = -1
        this.distancias.forEach {
            if (it > maiorDistancia && it != infinito) {
                maiorDistancia = it
            }
        }
        //println("DIAMETRO Matriz Adj -> $maiorDistancia")
        this.diametroMatriz = maiorDistancia
    }

    fun calcularExcentricidadeListaAdj(grafo: MutableList<MutableList<Int>>) {
        println("Calculando diametro usando lista adj ...")
        val listaDistanciaMaximaPorVertice: MutableList<Int> = mutableListOf()

        for (i in 0 until this.vertices) {
            buscaEmLarguraLista(grafo, i)
            listaDistanciaMaximaPorVertice.add(pegarMaiorDaLista(this.distancias))
        }

        /*println("DISTANCIAS")
        println(listaDistanciaMaximaPorVertice.toString())*/

        //buscar maior distancia
        var maiorDistancia = -1
        this.distancias.forEach {
            if (it > maiorDistancia && it != infinito) {
                maiorDistancia = it
            }
        }
        //println("DIAMETRO Lista Adj -> $maiorDistancia")
        this.diametroLista = maiorDistancia
    }
}