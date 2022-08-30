class Grafo(private val txt: String) {
    private var matrizAdj: MutableList<MutableList<Int>> = mutableListOf()
    private val listTxt: MutableList<Int> = mutableListOf()
    private var vertices = 0
    private var arestas = 0

    init {
        processarEntradaInserirMatrizAdj()
        val v = iniciar()
        //println("RESULTADO")
        println(v)
    }

    private fun processarEntradaInserirMatrizAdj() {
        val aux = txt.replace(" ".toRegex(), "\n")
        val text = aux.split("\n").toTypedArray()
        for (s in text) {
            listTxt.add(s.replace("\r", "").toInt())
        }
        vertices = listTxt[0]
        arestas = listTxt[1]

        inserirGrafo()
    }

    fun inserirGrafo() {
        val matriz = iniciarMatriz()

        for (i in 2 until this.listTxt.size step 3) {
            val line = listTxt[i] - 1
            val column = listTxt[i + 1] - 1
            val value = listTxt[i + 2]
            var listaAux = matriz[line]
            listaAux[column] = value
            matriz[line] = listaAux
            listaAux = matriz[column]
            listaAux[line] = value
            matriz[column] = listaAux
        }

        this.matrizAdj = matriz

        /*this.matrizAdj.forEach {
            println(it.toString())
        }*/
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

    private fun iniciar(): Int {

        //procura os vertices que possuem grau impar
        var impares = achaImpares(matrizAdj)

        //se nao houver vertice com grau impar ou somente 2
        if (impares.size == 0 || impares.size == 2) {
            return somaArestas(matrizAdj)
        }

        //todas as duplas possiveis para os vertices de grau impar
        var duplas = geraDuplas(impares)

        var pesos: MutableList<MutableList<Int>> = mutableListOf()

        //verifica o peso de todas as duplas
        duplas.forEach {
            if (it.isNotEmpty()) {
                pesos.add(
                    mutableListOf(
                        dijkstra(matrizAdj, it[0], it[1]),
                        it[0], //origem
                        it[1] // destino
                    )
                )
            }
        }
        pesos.sortBy {
            it[0] //ordenar por peso
        }

        var valor = somaArestas(matrizAdj)

        for (i in 0 until (impares.size - 2).floorDiv(2)) {
            val p = pesos[i]
            var remove = 0
            valor += p[0]
            pesos.forEachIndexed { index, peso ->
                val origemDestino = listOf(p[1], p[2])
                if (origemDestino.contains(peso[1]) || origemDestino.contains(peso[2])) {
                    remove = index
                }
            }
            pesos.removeAt(remove)
        }
        return valor
    }

    private fun somaArestas(grafo: MutableList<MutableList<Int>>): Int {
        var soma = 0
        for (i in 0 until grafo.size) {
            for (j in i until grafo.size) {
                soma += grafo[i][j]
            }
        }
        return soma
    }

    private fun dijkstra(
        grafo: MutableList<MutableList<Int>>,
        origem: Int,
        destino: Int
    ): Int {
        if (origem == destino)
            return 0

        var maisCurto: MutableList<Int> = mutableListOf()

        for (i in 0 until grafo.size) {
            maisCurto.add(0)
        }

        var selecionado: MutableList<Int> = mutableListOf(origem)
        var indice = 0

        var inf = 100000
        var minSelecionado = inf
        for (i in 0 until grafo.size) {
            if (i == origem) {
                maisCurto[i] = 0
            } else {
                if (grafo[origem][i] == 0) {
                    maisCurto[i] = inf
                } else {
                    maisCurto[i] = grafo[origem][i]
                    if (maisCurto[i] < minSelecionado) {
                        minSelecionado = maisCurto[i]
                        indice = i
                    }
                }
            }
        }
        selecionado.add(indice)
        while (indice != destino) {
            for (i in 0 until grafo.size) {
                if (!selecionado.contains(i)) {
                    if (grafo[indice][i] != 0) {
                        if ((grafo[indice][i] + minSelecionado) < maisCurto[i]) {
                            maisCurto[i] = grafo[indice][i] + minSelecionado
                        }
                    }
                }
            }
            var temp = 1000000

            for (j in 0 until grafo.size) {
                if (!selecionado.contains(j)) {
                    if (maisCurto[j] < temp) {
                        temp = maisCurto[j]
                        indice = j
                    }
                }
            }
            minSelecionado = temp
            selecionado.add(indice)
        }

        return maisCurto[destino]
    }

    private fun achaImpares(grafo: MutableList<MutableList<Int>>): MutableList<Int> {
        var impares: MutableList<Int> = mutableListOf()
        impares.clear()
        grafo.forEachIndexed { index, list ->
            var count = 0
            list.forEach {
                if (it != 0)
                    count++
            }
            if (count.mod(2) != 0)
                impares.add(index)
        }
        return impares
    }

    private fun geraDuplas(impares: MutableList<Int>): MutableList<MutableList<Int>> {
        var pares: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until impares.size) {
            val list: MutableList<Int> = mutableListOf()
            pares.add(list)
            for (j in i + 1 until impares.size) {
                pares[i].add(impares[i])
                pares[i].add(impares[j])
            }
        }
        return pares
    }

}