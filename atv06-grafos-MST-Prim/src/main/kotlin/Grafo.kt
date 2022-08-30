class Grafo(val txt: String) {
    private var matrizAdj: MutableList<MutableList<Int>> = mutableListOf()

    private val listTxt: MutableList<Int> = mutableListOf()
    private var vertices = 0
    private var arestas = 0

    init {
        processarEntradaInserirMatrizAdj()

        println()
        println("MATRIZ ADJ")
        for (i in 0 until this.vertices) {
            println(this.matrizAdj[i].toString())
        }
        val custo = Prim(this.vertices).prim(this.matrizAdj)

        val custoFinal = getAllCost(this.matrizAdj) - custo

        println()
        println()
        println("ECONOMIA DA PMA -> $custoFinal")
    }

    private fun getAllCost(graph: MutableList<MutableList<Int>>): Int {
        var cost = 0

        for (i in 0 until this.vertices) {
            for (j in i until this.vertices) {
                cost += graph[i][j]
            }
        }
        return cost
    }


    private fun processarEntradaInserirMatrizAdj() {
        val aux = txt.replace(" ".toRegex(), "\n")
        val text = aux.split("\n").toTypedArray()
        for (s in text) {
            listTxt.add(s.replace("\r", "").toInt())
        }
        vertices = listTxt[0]
        arestas = listTxt[1]

        println("VERTICES -> ${this.vertices}")
        println("ARESTAS -> ${this.arestas}")

        inserirGrafo()
    }

    fun inserirGrafo() {
        val matriz = iniciarMatriz()

        for (i in 2 until this.listTxt.size step 3) {
            val line = listTxt[i]
            val column = listTxt[i + 1]
            val value = listTxt[i + 2]
            /*var listaAux = matriz[line]
            listaAux[column] = value
            matriz[line] = listaAux
            matriz[column][line] = value*/

            var listaAux = matriz[column]
            listaAux[line] = value
            matriz[column] = listaAux
            matriz[line][column] = value
        }

        this.matrizAdj = matriz
    }

    fun iniciarMatriz(): MutableList<MutableList<Int>> {
        val matriz: MutableList<MutableList<Int>> = ArrayList()
        for (i in 0 until vertices) {
            val l: MutableList<Int> = ArrayList()
            for (j in 0 until vertices) {
                l.add(0)
            }
            matriz.add(l)
        }
        return matriz
    }
}