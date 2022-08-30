import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class Otimizacao(
    val path: String, val quantidadeDeMaquinas: Int
) {

    private val matrizPrecedencia: MutableList<MutableList<Int>> = mutableListOf()
    private var numeroTarefas: Int = 0
    private val custosDasTarefas: MutableList<Int> = mutableListOf()
    private val listaNumeroPrecedentes: MutableList<Int> = mutableListOf()
    private var listaSequenciaTarefas: MutableList<Int> = mutableListOf()
    private val maquinas: MutableList<MutableList<Int>> = mutableListOf()
    private val maquinasAleatorias: MutableList<MutableList<Int>> = mutableListOf()
    private var valorFO = -1
    private var listaAdicionados: MutableList<Int> = mutableListOf()
    private var qntPorMaquina: Int = 0
    private val listaSequenciaTarefasFinal: MutableList<Int> = mutableListOf()
    private var melhorFO = 1000000
    private var melhorMaquina: MutableList<MutableList<Int>> = mutableListOf()
    private var maquinaEscolhidaMelhor: Int = -1
    private var tarefaEscolhidaMelhor: Int = -1

    //alteracoes
    private var listaTarefas: MutableList<Tarefa> = mutableListOf()
    private var maquinasPrincipais: List<List<Int>> = mutableListOf() //SALVA A SOLUCAO INICIAL
    private val maquinaComVerificacaoVizinho: MutableList<MutableList<Int>> =
        mutableListOf() //SALVA A SOLUCAO MELHOR COM VIZINHO
    private var melhorFOVizinho: Int = 10000000

    init {
        processText()
    }

    fun iniciarMatrizPrecedencia() {
        for (i in 0 until numeroTarefas) {
            var list: MutableList<Int> = mutableListOf()

            for (j in 0 until numeroTarefas) {
                list.add(0)
            }
            this.matrizPrecedencia.add(list)
        }
        for (i in 0 until numeroTarefas) {
            println(this.matrizPrecedencia[i].toString())
        }

    }

    fun criarListaDeTarefas(listaSequenciaTarefas: MutableList<Int>) {
        val lista: MutableList<Tarefa> = mutableListOf()

        listaSequenciaTarefas.forEach {
            val position = listaSequenciaTarefas.indexOf(it)
            var listaPredecessores: MutableList<Int> = mutableListOf()
            var listaSucessores: MutableList<Int> = mutableListOf()

            listaPredecessores.clear()
            listaSucessores.clear()
            //predecessores
            for (i in 0 until position) {
                listaPredecessores.add(listaSequenciaTarefas[i])
            }
            //sucessores
            for (i in position until listaSequenciaTarefas.size) {
                listaSucessores.add(listaSequenciaTarefas[i])
            }

            listaPredecessores.remove(it)
            listaSucessores.remove(it)

            this.listaTarefas.add(
                Tarefa(
                    it,
                    listaPredecessores,
                    listaSucessores
                )
            )
        }

        this.listaTarefas.sortBy {
            it.idTarefa
        }

        println("LISTA DE TAREFAS ++++++++++++++++++++++")
        for (i in 0 until listaSequenciaTarefas.size) {
            println(" ++++++ TAREFA $i ++++++")
            println(" ++++++ LISTA PREDECESSORES ++++++")
            println(this.listaTarefas[i].predecessores.toString())
            println(" ++++++ LISTA SUCESSORES ++++++")
            println(this.listaTarefas[i].sucessores.toString())
        }
        println("FIM LISTA DE TAREFAS ++++++++++++++++++++++")

    }

    fun processText() {
        var list: MutableList<String> = mutableListOf()
        var initial = 0
        var listCustos: MutableList<Int> = mutableListOf()

        try {
            BufferedReader(FileReader(path)).use { br ->
                var line: String?
                br.readLine().also {
                    numeroTarefas = it.toInt()
                }
                iniciarMatrizPrecedencia()
                for (i in 0 until numeroTarefas) {
                    br.readLine().also {
                        custosDasTarefas.add(it.toInt())
                    }
                }
                while (br.readLine().also { line = it } != null) {
                    val linha = line!!.substringBefore(",").toInt()
                    val coluna = line!!.substringAfter(",").toInt()

                    if (linha != -1 && coluna != -1) this.matrizPrecedencia[linha - 1][coluna - 1] = 1
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        for (i in 0 until this.numeroTarefas) {
            this.listaNumeroPrecedentes.add(0)
        }
        for (i in 0 until this.numeroTarefas) {
            for (j in 0 until this.numeroTarefas) {
                if (this.matrizPrecedencia[i][j] != 0) {
                    this.listaNumeroPrecedentes[i]++
                }
            }
        }



        println("======= MATRIZ PRECEDENCIA =======")
        for (i in 0 until this.numeroTarefas) {
            println(this.matrizPrecedencia[i].toString())
        }
        println("======= LISTA NUMERO PRECENTES =======")
        println(this.listaNumeroPrecedentes.toString())

        criarPrecedenciaDeTarefas()
    }

    fun criarPrecedenciaDeTarefas() {
        if (this.numeroTarefas / quantidadeDeMaquinas == 0) {
            this.qntPorMaquina = 1
        } else {
            this.qntPorMaquina = this.numeroTarefas / quantidadeDeMaquinas
        }

        val listaCandidatos: MutableList<Int> = mutableListOf()

        for (i in 0 until this.numeroTarefas) {
            if (this.listaNumeroPrecedentes[i] == 0) {
                listaCandidatos.add(i)
            }
        }

        //iniciar maquinas
        for (i in 0 until quantidadeDeMaquinas) {
            this.maquinas.add(mutableListOf())
        }

        /*println("---LISTA CANDIDATOS---")
        println(listaCandidatos.toString())
        println("---qnt por maquina---")
        println(qntPorMaquina)*/

        var candidato = 0 // criar sequencia de tarefas
        while (listaCandidatos.isNotEmpty()) {
            listaSequenciaTarefas.add(listaCandidatos.first())
            listaCandidatos.removeFirst()

            candidato = listaSequenciaTarefas.last()

            for (i in 0 until this.numeroTarefas) {
                if (this.matrizPrecedencia[i][candidato] != 0) {
                    listaCandidatos.add(i)
                    this.matrizPrecedencia[i][candidato] = 0
                }
            }
        }

        //tirar tarefas repetidas
        val listaSequenciaSemRepeticao = this.listaSequenciaTarefas.toSet().toList()
        this.listaSequenciaTarefas = listaSequenciaSemRepeticao.toMutableList()

        println(" ================== LISTA SEQUENCIA DE TAREFAS ================== ")
        println(this.listaSequenciaTarefas.toString())
        println()

        criarListaDeTarefas(this.listaSequenciaTarefas)

        this.listaAdicionados.clear()
        //lista de tarefas que ja foram adicionadas a alguma maquina
        //val listaDeAdicionados: MutableList<Int> = mutableListOf()

        /*println("++++ LISTA SEQUENCIA DE TAREFAS ++++")
        println(this.listaSequenciaTarefas.toString())*/

        val fila: MutableList<Int> = mutableListOf()
        while (this.listaSequenciaTarefas.isNotEmpty()) {
            fila.add(this.listaSequenciaTarefas.first())
            this.listaSequenciaTarefas.removeFirst()

            if (fila.size == (quantidadeDeMaquinas * qntPorMaquina)) {
                for (j in 0 until quantidadeDeMaquinas) {
                    for (k in 0 until qntPorMaquina) {
                        maquinas[j].add(fila.first())
                        this.listaAdicionados.add(fila.first())
                        fila.removeFirst()
                    }
                }
                fila.clear()
            }

        }

        /*println("RESTO")
        println(fila.toString())*/


        if (fila.isNotEmpty()) {
            for (i in (fila.size - 1) downTo 0) {
                for (j in quantidadeDeMaquinas - 1 downTo 0) {
                    if (fila.isNotEmpty()) {
                        maquinas[j].add(fila.first())
                        this.listaAdicionados.add(fila.first())
                        fila.removeFirst()
                    }
                }
            }
        }

        /*println("++++ LISTA ADICIONADOS ++++")
        println(this.listaAdicionados.toString())*/

        for (i in 0 until this.listaAdicionados.size) {
            this.listaSequenciaTarefas.remove(this.listaAdicionados[i])
        }

        /*for(i in this.listaSequenciaTarefas.size - 1 downTo 0) {
            maquinas[i].add(this.listaSequenciaTarefas.first())
            this.listaSequenciaTarefas.removeFirst()
        }*/



        println(listaCandidatos.toString())

        for (i in 0 until maquinas.size) {
            print("MAQUINA $i ->")
            println(maquinas[i].toString())
        }

        calcularFO(maquinas, -1, -1)
        //println(listaSequenciaSemRepeticao.toString())

        /*if (qntPorMaquina > 1) {
            for (i in 0..5000) {
                if (quantidadeDeMaquinas > 1) aleatoriedade()
            }
        }*/


        val maquinasParaTras: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until maquinas.size) {
            maquinasParaTras.add(mutableListOf())
            maquinas[i].forEach {
                maquinasParaTras[i].add(it)
            }
        }

        val maquinasParaFrente: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until maquinas.size) {
            maquinasParaFrente.add(mutableListOf())
            maquinas[i].forEach {
                maquinasParaFrente[i].add(it)
            }
        }

        for (i in 0..100) {
            val maquinasAleatorias = aleatoriedade(true, 0, 0) ?: maquinas
            //verificarVizinho(maquinas)


            for (i in 0..100) {
                val maquinasParaTras: MutableList<MutableList<Int>> = mutableListOf()
                for (i in 0 until maquinasAleatorias.size) {
                    maquinasParaTras.add(mutableListOf())
                    maquinasAleatorias[i].forEach {
                        maquinasParaTras[i].add(it)
                    }
                }

                val maquinasParaFrente: MutableList<MutableList<Int>> = mutableListOf()
                for (i in 0 until maquinasAleatorias.size) {
                    maquinasParaFrente.add(mutableListOf())
                    maquinasAleatorias[i].forEach {
                        maquinasParaFrente[i].add(it)
                    }
                }

                val rand = Random()

                var iguais = 1
                var primeiroSize = maquinas.first().size
                maquinasAleatorias.forEach {
                    if (it.size != primeiroSize) {
                        iguais = 0
                    }
                }

                var tarefa = -1

                if (i != 0) {

                    var mEscolhida = rand.nextInt(maquinasAleatorias.size)

                    while (maquinasAleatorias[mEscolhida].size == null)
                        mEscolhida = rand.nextInt(maquinasAleatorias.size)


                    if (maquinasAleatorias[mEscolhida].size > 0) {
                        val posicaoTarefa = rand.nextInt(maquinasAleatorias[mEscolhida].size)
                        tarefa = maquinasAleatorias[mEscolhida][posicaoTarefa]
                    }
                } else {

                    var maiorSize = -1
                    var mEscolhida = -1
                    maquinasAleatorias.forEachIndexed { i, list ->
                        if (list.size > maiorSize) {
                            maiorSize = list.size
                            mEscolhida = i
                        }
                    }
                    val posicaoTarefa = rand.nextInt(maquinasAleatorias[mEscolhida].size)

                    tarefa = maquinasAleatorias[mEscolhida][posicaoTarefa]
                }

                if (tarefa >= 0) {
                    fazerVizinhoPraFrente(maquinasParaFrente, tarefa)
                    fazerVizinhoPraTras(maquinasParaTras, tarefa)
                }
            }
        }
        imprimirSolucao()

        println("--------  FINAL ALGORITMO  --------")
        println(" ++++ MAQUINA INICIAL ++++ ")
        for (i in 0 until this.maquinasPrincipais.size)
            println(this.maquinasPrincipais[i].toString())
        println("FO -> ${this.melhorFO}")
        println(" ++++++++++++++++++++++++ ")
        println(" ++++ MAQUINA VIZINHOS ++++ ")
        for (i in 0 until this.maquinaComVerificacaoVizinho.size)
            println(this.maquinaComVerificacaoVizinho[i].toString())
        println("FO Vizinhos-> ${this.melhorFOVizinho}")
        println(" ++++++++++++++++++++++++ ")

        println("CUSTOS")
        for (i in 0 until this.custosDasTarefas.size) {
            println("TAREFA $i -> ${this.custosDasTarefas[i]}")
        }

        BLMM(this.melhorMaquina, this.listaSequenciaTarefas)
        /*println("++++++++++++++")
        println("MAQUINA ESCOLHIDA")
        println(this.maquinaEscolhidaMelhor)
        println("TAREFA ESCOLHIDA")
        println(this.tarefaEscolhidaMelhor)*/

    }

    fun preencherUmaMaquina() {
        while (listaSequenciaTarefas.isNotEmpty()) {
            // maquinas[0].add()
        }
    }

    fun aleatoriedade(rand: Boolean, tarefa: Int, maquina: Int): MutableList<MutableList<Int>>? {

        var tarefaEscolhida = -1
        var maquinaEscolhida = -1
        var posicao: Int = -1
        var maquinaAtualDaTarefa = -1

        if (rand) {
            val rand = Random()

            maquinasAleatorias.clear()

            tarefaEscolhida = rand.nextInt(this.numeroTarefas)

            for (i in 0 until this.maquinas.size) {
                if (this.maquinas[i].contains(tarefaEscolhida)) maquinaAtualDaTarefa = i
            }

            maquinaEscolhida = rand.nextInt(quantidadeDeMaquinas)

            while (maquinaEscolhida == maquinaAtualDaTarefa) maquinaEscolhida = rand.nextInt(quantidadeDeMaquinas)

            posicao = this.listaAdicionados.indexOf(tarefaEscolhida)
        } else {
            maquinasAleatorias.clear()

            tarefaEscolhida = tarefa

            for (i in 0 until this.maquinas.size) {
                if (this.maquinas[i].contains(tarefaEscolhida)) maquinaAtualDaTarefa = i
            }

            maquinaEscolhida = maquina

            posicao = this.listaAdicionados.indexOf(tarefaEscolhida)
        }


        var aux: MutableList<Int> = mutableListOf()
        aux.addAll(this.listaAdicionados)

        //val filaAte = this.listaAdicionados.subList(0, posicao+1)
        val filaAte: MutableList<Int> = mutableListOf()

        for (i in 0 until posicao) {
            filaAte.add(this.listaAdicionados[i])
        }

        val filaApos: MutableList<Int> = mutableListOf()
        for (i in posicao until this.listaAdicionados.size) {
            filaApos.add(this.listaAdicionados[i])
        }

        /*
        println("+++++++FILAS ATE E APOS+++++++")
        println(filaAte.toString())
        println(filaApos.toString())
        println()

         */

        for (i in 0 until quantidadeDeMaquinas) maquinasAleatorias.add(mutableListOf())

        val filaExecucao: MutableList<Int> = mutableListOf()
        var tarefasPorMaquina: Int =
            if (maquinaEscolhida != 0) filaAte.size / (maquinaEscolhida) else filaAte.size / 1
        if (tarefasPorMaquina == 0) tarefasPorMaquina = 1
        val tamanhoFilaAte = filaAte.size

        filaExecucao.clear()
        while (filaAte.isNotEmpty()) {
            filaExecucao.add(filaAte.first())
            filaAte.removeFirst()
        }

        for (j in 0 until maquinaEscolhida) {
            for (k in 0..tarefasPorMaquina) {
                if (filaExecucao.isNotEmpty()) {
                    maquinasAleatorias[j].add(filaExecucao.first())
                    filaExecucao.removeFirst()
                }
            }
        }

        filaExecucao.clear()

        //val filaApos: MutableList<Int> = mutableListOf()
        //filaApos.addAll(this.listaAdicionados)
        //filaApos.removeFirst()

        filaExecucao.clear()

        tarefasPorMaquina = filaApos.size / (quantidadeDeMaquinas - maquinaEscolhida)
        if (tarefasPorMaquina == 0) tarefasPorMaquina = 1

        while (filaApos.isNotEmpty()) {
            filaExecucao.add(filaApos.first())
            filaApos.removeFirst()
        }

        for (j in maquinaEscolhida until quantidadeDeMaquinas) {
            for (k in 0 until tarefasPorMaquina) {
                if (filaExecucao.isNotEmpty()) {
                    maquinasAleatorias[j].add(filaExecucao.first())
                    filaExecucao.removeFirst()
                }
            }
        }

        //verificar viabilidade
        val listaVerificacao: MutableList<Int> = mutableListOf()
        listaVerificacao.addAll(this.listaAdicionados)
        val listaVerificacaoBoolean: MutableList<Boolean> = mutableListOf()
        for (i in 0 until listaVerificacao.size)
            listaVerificacaoBoolean.add(false)

        /* println("++++++++++ LISTA VERIFICAÇÃO ANTES ++++++++++")
         println(listaVerificacao.toString())
         */

        var verificarPorTamanho = 0
        for (i in 0 until maquinasAleatorias.size) {
            verificarPorTamanho += maquinasAleatorias[i].size
        }

        val preencheuTudo: Boolean = verificarPorTamanho == listaVerificacao.size

        /*println("----------- EXECUCAO MAQUINAS ALEATORIAS -----------")
        for (i in 0 until maquinasAleatorias.size) println(maquinasAleatorias[i].toString())

        println("++++++++++ LISTA VERIFICAÇÃO ++++++++++")
        println(listaVerificacao.toString())*/

        if (preencheuTudo) {
            //calcularFO(maquinasAleatorias, maquinaEscolhida, tarefaEscolhida)
            return maquinasAleatorias
        }

        listaVerificacao.clear()
        return null
    }

    fun verificarVizinho(maquinas: List<List<Int>>) {
        val rand = Random()
        var maquina = 0 // A maquina escolhida e a que possui maior numero de elementos
        var ultimaMaiorSize = 0


        // verifica se todas as maquinas ficaram com quantidade de tarefas iguais
        var iguais = 1
        for (i in 0 until maquinas.size - 1) {
            if (maquinas[i].size != maquinas[i + 1].size)
                iguais = 0
        }

        if (iguais != 1) {
            for (i in 0 until maquinas.size) {
                if (maquinas[i].size > ultimaMaiorSize)
                    maquina = i
                ultimaMaiorSize = maquinas[i].size
            }
        } else {
            maquina = rand.nextInt(maquinas.size)
            while (maquina == 0)
                maquina = rand.nextInt(maquinas.size)
        }
        println()
        println(" ++++++++++++++++++++ MAQUINA ESCOLHIDA ++++++++++++++++++++++++")
        println("Maquina -> $maquina")
        println()

        var posicaoTarefaInicio = rand.nextInt(maquinas[maquina].size)
        //var tarefaInicio = maquinas[maquina][posicaoTarefaInicio]
        var tarefaInicio = 15
        //var tarefaFim = maquinas[maquina].last()


        println()
        println(" ++++++++++++++++++++ TAREFA ESCOLHIDA ++++++++++++++++++++++++")
        println("TAREFA -> $tarefaInicio")
        println()

        var maquinaAuxiliar: MutableList<MutableList<Int>> = mutableListOf()

        for (i in 0 until maquinas.size)
            maquinaAuxiliar.add(maquinas[i].toMutableList()) // copia maquinas para maquinas auxiliar
        println("MAQUINA AUXILIAR =====================")
        for (i in 0 until maquinas.size)
            maquinaAuxiliar[i].toString()

        var maquinaDaTarefa = 0
        for (i in 0 until maquinas.size) {
            if (maquinas[i].contains(tarefaInicio))
                maquinaDaTarefa = i
        }

        var listaTarefasReorganizar: MutableList<Int> = mutableListOf()

        //reorganizar as tarefas
        for (i in 0..maquinas[maquinaDaTarefa].indexOf(tarefaInicio))
            listaTarefasReorganizar.add(maquinas[maquinaDaTarefa][i])

        println()
        println(" ++++++++++++++++++++ LISTA REORGANIZAR ++++++++++++++++++++++++")
        println(listaTarefasReorganizar.toString())
        println()

/*
        // " OLHA PRA TRAS "
        //tentar colocar na maquina anterior
        if (maquinaDaTarefa >= 1) {
            //verificar maquina anterior
            val listaPredecessores = this.listaTarefas[tarefaInicio].predecessores

            //verificar se a ultima tarefa da maquina anterior e o predecessor
            if (maquinas[maquinaDaTarefa - 1].contains(listaPredecessores.last())) {
                for (i in 0 until listaTarefasReorganizar.size) {
                    maquinaAuxiliar[maquinaDaTarefa - 1].add(listaTarefasReorganizar[i])
                    maquinaAuxiliar[maquinaDaTarefa].remove(listaTarefasReorganizar[i])
                }
            }
        }

        if (maquinaDaTarefa <= maquinas.size - 1) {
            //verificar maquina anterior
            val listaSucessores = this.listaTarefas[tarefaInicio].sucessores

            //verificar se a ultima tarefa da maquina anterior e o predecessor
            if (maquinas[maquinaDaTarefa + 1].contains(listaSucessores.first())) {
                for (i in 0 until listaTarefasReorganizar.size) {
                    maquinaAuxiliar[maquinaDaTarefa - 1].add(listaTarefasReorganizar[i])
                    maquinaAuxiliar[maquinaDaTarefa].remove(listaTarefasReorganizar[i])
                }
            }
        }

        println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES REORGANIZADAS ++++++++++++++++++++++++")
        for (i in 0 until maquinaAuxiliar.size)
            println(maquinaAuxiliar[i].toString())
        println()

        calcularFOVizinho(maquinas, maquinaAuxiliar, maquinaDaTarefa, tarefaInicio)
*/
        //maquinaAuxiliar.clear()
        //maquinaAuxiliar.add(mutableListOf())
        var maquinaAuxiliarSucessores: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until maquinas.size)
            maquinaAuxiliarSucessores.add(maquinas[i].toMutableList()) // copia maquinas para maquinas auxiliar

        println("MAQUINA AUXILIAR 2 =====================")
        for (i in 0 until maquinas.size)
            println(maquinaAuxiliarSucessores[i].toString())
        println("+++++++++=====================")


        listaTarefasReorganizar.clear()
        //reorganizar as tarefas
        /* for (i in maquinas[maquinaDaTarefa].indexOf(tarefaInicio) until maquinas[maquinaDaTarefa].size)
             listaTarefasReorganizar.add(maquinas[maquinaDaTarefa][i])*/

        println()
        println(" ++++++++++++++++++++ LISTA REORGANIZAR 2 ++++++++++++++++++++++++")
        println(listaTarefasReorganizar.toString())
        println()

        if (maquinaDaTarefa <= maquinas.size - 1) {
//            val tarefaSucessora = this.listaTarefas[listaTarefasReorganizar.last()].sucessores
            println("TAREFA SUCESSORA DA LISTA REORGANIZAR")
            // println(this.listaTarefas[listaTarefasReorganizar.last() + 1].idTarefa)

            //verificar se a ultima tarefa da maquina anterior e o predecessor
            /*if (maquinas[maquinaDaTarefa + 1].contains(listaSucessores.first())) {

                val listaAux : MutableList<Int> = mutableListOf()
                listaAux.addAll(listaTarefasReorganizar)
                listaAux.addAll(maquinaAuxiliarSucessores[maquinaDaTarefa + 1])

                println()
                println(" ++++++++++++++++++++ LISTA AUX DENTRO ++++++++++++++++++++++++")
                println(listaAux.toString())
                println()

                maquinaAuxiliarSucessores[maquinaDaTarefa + 1].clear()
                maquinaAuxiliarSucessores[maquinaDaTarefa + 1].addAll(listaAux)
                maquinaAuxiliarSucessores[maquinaDaTarefa].removeAll(listaAux)

            }*/
        }

        calcularFOVizinho(maquinas, maquinaAuxiliarSucessores, maquinaDaTarefa, tarefaInicio)
    }

    fun calcularFOVizinho(
        maquinaInicial: List<List<Int>>,
        maquinasExecucao: MutableList<MutableList<Int>>,
        maquinaEscolhida: Int,
        tarefaEscolhida: Int
    ) {
        var maiorValor = 0
        var FO = 0
        /*println("  ---------------------- CALCULAR FO SIZE 1 ----------------------  ")
        println(maquinasExecucao.size)
        for(i in 0 until maquinasExecucao.size)
            println(maquinasExecucao[i].toString())
        println("  ---------------------- CALCULAR FO SIZE 1 ----------------------  ")*/

        for (i in 0 until maquinasExecucao.size) {
            var result = 0
            for (j in 0 until maquinasExecucao[i].size) {
                result += this.custosDasTarefas[maquinasExecucao[i][j]]
            }
            if (result > maiorValor) maiorValor = result
        }

        /*println("++++ LISTA DE CUSTOS ++++")
        for (i in 0 until this.custosDasTarefas.size) {
            print("Tarefa $i -> ")
            println(this.custosDasTarefas[i])
        }
        println()*/


        println("+++++++++++++++++++++")
        println("FO -> $maiorValor")
        println("+++++++++++++++++++++")
        FO = maiorValor

        if (FO < this.melhorFOVizinho) {

            this.maquinaComVerificacaoVizinho.clear()

            /*println("  ---------------------- MAQUINA SIZE 1 ----------------------  ")
            println(this.melhorMaquina.size)
            println("  ---------------------- MAQUINA SIZE 1 ----------------------  ")*/

            this.maquinaComVerificacaoVizinho.addAll(maquinasExecucao)

            /*println("  ---------------------- MAQUINA SIZE 2 ----------------------  ")
            println(this.melhorMaquina.size)
            println("  ---------------------- MAQUINA SIZE 2 ----------------------  ")*/

            this.melhorFOVizinho = FO
            this.maquinasPrincipais = maquinaInicial
        }
    }

    fun fazerVizinhoPraTras(maquinas: MutableList<MutableList<Int>>, tEscolhida: Int) {
        println(" INICIO VIZINHO PRA tras -----------------")
        println()
        println("CHEGUEI AQU")

        val maquinasBackup: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until maquinas.size) {
            maquinasBackup.add(mutableListOf())
            maquinas[i].forEach {
                maquinasBackup[i].add(it)
            }
        }

        println()
        println(" ++++++++++++++++++++ MAQUINAS PRINCIPAIS 1 ++++++++++++++++++++++++")
        for (i in 0 until maquinas.size)
            println(maquinas[i].toString())
        println()


        val listaExecucaoTarefas = this.listaSequenciaTarefas
        val tarefaEscolhida = tEscolhida
        var maquinaAtualTarefaEscolhida = -1
        var posicaoAtualTarefaEscolhida = -1

        //copiar maquinas principais para uma maquinaAuxiliar
        val maquinaExecucaoAuxiliar: MutableList<MutableList<Int>> = mutableListOf()

        for (i in 0 until maquinas.size) {
            maquinaExecucaoAuxiliar.add(maquinas[i])
        }

        /*println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()*/

        //pegar maquina da tarefa escolhida
        for (i in 0 until maquinas.size) {
            if (maquinas[i].contains(tarefaEscolhida)) {
                maquinaAtualTarefaEscolhida = i
                posicaoAtualTarefaEscolhida = maquinas[i].indexOf(tarefaEscolhida)
            }
        }

        println("MAQUINA ATUAL -> $maquinaAtualTarefaEscolhida")

        //pra tras
        val listaTarefaEscolhidaParaTras: MutableList<Int> = mutableListOf()

        for (i in 0..posicaoAtualTarefaEscolhida) {
            listaTarefaEscolhidaParaTras.add(maquinas[maquinaAtualTarefaEscolhida][i])
        }
        //separa a lista para tras
        if (maquinaAtualTarefaEscolhida >= 1) {
            maquinaExecucaoAuxiliar[maquinaAtualTarefaEscolhida - 1].addAll(listaTarefaEscolhidaParaTras)
            maquinaExecucaoAuxiliar[maquinaAtualTarefaEscolhida].removeAll(listaTarefaEscolhidaParaTras)
        }


        println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES REORGANIZADAS ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()

        println()
        println(" ++++++++++++++++++++ MAQUINAS PRINCIPAIS ++++++++++++++++++++++++")
        for (i in 0 until maquinas.size)
            println(maquinas[i].toString())
        println()
        println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()
        println()
        println(" ++++++++++++++++++++ MAQUINAS BACKUP ++++++++++++++++++++++++")
        for (i in 0 until maquinasBackup.size)
            println(maquinasBackup[i].toString())
        println()


        calcularFOVizinho(maquinasBackup, maquinaExecucaoAuxiliar, maquinaAtualTarefaEscolhida, tarefaEscolhida)



        println(" FIM VIZINHO PRA tras -----------------")
        println()
    }

    fun fazerVizinhoPraFrente(maquinas: MutableList<MutableList<Int>>, tEscolhida: Int) {
        println(" INICIO VIZINHO PRA FRENTE -----------------")
        println()
        println("CHEGUEI AQU")

        val maquinasBackup: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until maquinas.size) {
            maquinasBackup.add(mutableListOf())
            maquinas[i].forEach {
                maquinasBackup[i].add(it)
            }
        }

        println()
        println(" ++++++++++++++++++++ MAQUINAS PRINCIPAIS 1 ++++++++++++++++++++++++")
        for (i in 0 until maquinas.size)
            println(maquinas[i].toString())
        println()


        val listaExecucaoTarefas = this.listaSequenciaTarefas
        val tarefaEscolhida = tEscolhida
        var maquinaAtualTarefaEscolhida = -1
        var posicaoAtualTarefaEscolhida = -1

        //copiar maquinas principais para uma maquinaAuxiliar
        val maquinaExecucaoAuxiliar: MutableList<MutableList<Int>> = mutableListOf()

        for (i in 0 until maquinas.size) {
            maquinaExecucaoAuxiliar.add(maquinas[i])
        }

        /*println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()*/

        //pegar maquina da tarefa escolhida
        for (i in 0 until maquinas.size) {
            if (maquinas[i].contains(tarefaEscolhida)) {
                maquinaAtualTarefaEscolhida = i
                posicaoAtualTarefaEscolhida = maquinas[i].indexOf(tarefaEscolhida)
            }
        }

        println("MAQUINA ATUAL -> $maquinaAtualTarefaEscolhida")

        //separar a lista do escolhido pra frente na maquina e passar pra proxima maquina no inicio dela

        //separar a lista
        val listaTarefaEscolhida: MutableList<Int> = mutableListOf()

        for (i in posicaoAtualTarefaEscolhida until maquinas[maquinaAtualTarefaEscolhida].size) {
            listaTarefaEscolhida.add(maquinas[maquinaAtualTarefaEscolhida][i])
        }

        println("Lista Tarefa Escolhida")
        println(listaTarefaEscolhida.toString())

        if (maquinaAtualTarefaEscolhida <= maquinas.size - 2) {
            val listaAuxiliar = listaTarefaEscolhida
            listaAuxiliar.addAll(maquinas[maquinaAtualTarefaEscolhida + 1])

            maquinaExecucaoAuxiliar[maquinaAtualTarefaEscolhida + 1].clear()
            maquinaExecucaoAuxiliar[maquinaAtualTarefaEscolhida + 1].addAll(listaAuxiliar)
            maquinaExecucaoAuxiliar[maquinaAtualTarefaEscolhida].removeAll(listaAuxiliar)
        }


        println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES REORGANIZADAS ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()

        println()
        println(" ++++++++++++++++++++ MAQUINAS PRINCIPAIS ++++++++++++++++++++++++")
        for (i in 0 until maquinas.size)
            println(maquinas[i].toString())
        println()
        println()
        println(" ++++++++++++++++++++ MAQUINAS AUXILIARES ++++++++++++++++++++++++")
        for (i in 0 until maquinaExecucaoAuxiliar.size)
            println(maquinaExecucaoAuxiliar[i].toString())
        println()
        println()
        println(" ++++++++++++++++++++ MAQUINAS BACKUP ++++++++++++++++++++++++")
        for (i in 0 until maquinasBackup.size)
            println(maquinasBackup[i].toString())
        println()


        calcularFOVizinho(maquinasBackup, maquinaExecucaoAuxiliar, maquinaAtualTarefaEscolhida, tarefaEscolhida)



        println(" FIM VIZINHO PRA FRENTE -----------------")
        println()
    }

    fun calcularFO(maquinasExecucao: MutableList<MutableList<Int>>, maquinaEscolhida: Int, tarefaEscolhida: Int): Int {
        var maiorValor = 0

        /*println("  ---------------------- CALCULAR FO SIZE 1 ----------------------  ")
        println(maquinasExecucao.size)
        for(i in 0 until maquinasExecucao.size)
            println(maquinasExecucao[i].toString())
        println("  ---------------------- CALCULAR FO SIZE 1 ----------------------  ")*/

        for (i in 0 until maquinasExecucao.size) {
            var result = 0
            for (j in 0 until maquinasExecucao[i].size) {
                result += this.custosDasTarefas[maquinasExecucao[i][j]]
            }
            if (result > maiorValor) maiorValor = result
        }

        /*println("++++ LISTA DE CUSTOS ++++")
        for (i in 0 until this.custosDasTarefas.size) {
            print("Tarefa $i -> ")
            println(this.custosDasTarefas[i])
        }
        println()*/


        println("+++++++++++++++++++++")
        println("FO -> $maiorValor")
        println("+++++++++++++++++++++")
        this.valorFO = maiorValor

        if (this.valorFO < this.melhorFO) {

            this.melhorMaquina.clear()

            /*println("  ---------------------- MAQUINA SIZE 1 ----------------------  ")
            println(this.melhorMaquina.size)
            println("  ---------------------- MAQUINA SIZE 1 ----------------------  ")*/

            this.melhorMaquina.addAll(maquinasExecucao)

            /*println("  ---------------------- MAQUINA SIZE 2 ----------------------  ")
            println(this.melhorMaquina.size)
            println("  ---------------------- MAQUINA SIZE 2 ----------------------  ")*/

            this.melhorFO = this.valorFO
            this.maquinaEscolhidaMelhor = maquinaEscolhida
            this.tarefaEscolhidaMelhor = tarefaEscolhida
            return this.melhorFO
        }
        return this.valorFO
    }

    fun imprimirSolucao() {
        println("============================")

        var txt = ""

        /*println("  ---------------------- MAQUINA SIZE ----------------------  ")
        println(this.melhorMaquina.size)
        println("  ---------------------- MAQUINA SIZE ----------------------  ")
        */
        for (i in 0 until this.melhorMaquina.size) {
            print("Maquina $i: ")
            txt += "Maquina $i: "
            for (j in 0 until this.melhorMaquina[i].size) {
                print("${this.melhorMaquina[i][j]} ")
                txt += "${this.melhorMaquina[i][j]} "
            }
            println()
            txt += "\n"
        }
        println("Custo: ${this.melhorFO}")
        txt += "Custo: ${this.melhorFO}"

        try {
            val tituloPre = path.substringBefore(".") + "-SOLUCAO"
            val tituloSuf = "." + path.substringAfter(".")
            val titulo = tituloPre + tituloSuf
            val arquivo = File(titulo)
            arquivo.writeText(txt)//aqui você irá passar os dados
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun BLMM(maquinas: MutableList<MutableList<Int>>, sequenciaExecucao: MutableList<Int>) {
        var tarefaEscolhida = -1
        var maquinaEscolhida = -1


        val solucao: MutableList<MutableList<Int>> = mutableListOf()



        var text = ""

        // println("NUMERO TAREFAS -> ${this.numeroTarefas}")
        for (j in 0 until this.numeroTarefas) {
            tarefaEscolhida = j
            text += "TAREFA ${j} \n"
            val precedentes = verificarPrecedentes(tarefaEscolhida)
            for (k in 0 until this.quantidadeDeMaquinas) {
                solucao.clear()
                for(i in 0 until maquinas.size) {
                    solucao.add(mutableListOf())
                    solucao[i].addAll(maquinas[i])
                }
                maquinaEscolhida = k

                //verificar maquina atual da tarefa
                var maquinaAtualTarefa = -1
                for (i in 0 until solucao.size) {
                    if (solucao[i].contains(tarefaEscolhida))
                        maquinaAtualTarefa = i
                }

                if (maquinaAtualTarefa != maquinaEscolhida) {
                    val result = aleatoriedade(false, tarefaEscolhida, maquinaEscolhida)

                    if (maquinaEscolhida < maquinaAtualTarefa) {
                        if (precedentes.isEmpty() || solucao[maquinaEscolhida].contains(precedentes.last())) { //entao pode inserir
                            solucao[maquinaEscolhida].add(tarefaEscolhida)
                            solucao[maquinaAtualTarefa].remove(tarefaEscolhida)
                        }
                    } else {
                        if (precedentes.isEmpty() || solucao[maquinaEscolhida].contains(precedentes.last())) { //entao pode inserir
                            var listAux: MutableList<Int> = mutableListOf()
                            listAux.add(tarefaEscolhida)
                            solucao[maquinaEscolhida].forEach {
                                listAux.add(it)
                            }
                            solucao[maquinaEscolhida].clear()
                            solucao[maquinaEscolhida].addAll(listAux)
                            solucao[maquinaAtualTarefa].remove(tarefaEscolhida)
                        }
                    }

                    val fo_result = calcularFO(solucao, maquinaEscolhida, tarefaEscolhida)
                    text += "\n"
                    for(x in 0 until solucao.size) {
                        text += "${solucao[x].toString()}\n"
                    }
                    text += "FO -> $fo_result \n\n"
                    text += "+++++++++++++++++++++++++++++\n\n"
                }
            }
        }
        try {
            val arquivo = File("BLMM-result.txt")
            arquivo.writeText(text)//aqui você irá passar os dados
        } catch(e: Exception) {
            println("ERROR -> ${e.message}")
        }
    }

    fun verificarPrecedentes(tarefa: Int): List<Int> {
        val listaPrecedentes: MutableList<Int> = mutableListOf()

        for (i in 0 until this.matrizPrecedencia.size) {
            this.matrizPrecedencia[i].forEachIndexed { index, n ->
                if (n == 1) {
                    listaPrecedentes.add(index)
                }
            }
        }

        return listaPrecedentes
    }
}