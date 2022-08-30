class Prim(private val vertices: Int) {
    private var V = vertices

    fun minKey(key: MutableList<Int>, mstSet: MutableList<Boolean>): Int {
        var min = Int.MAX_VALUE
        var min_index = -1

        for (v in 0 until this.V) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v]
                min_index = v
            }
        }
        return min_index
    }

    fun printPrim(parent: MutableList<Int>, graph: MutableList<MutableList<Int>>) {
        println()
        println("+++++++++++++++")
        print("PARENT ->  ")
        println(parent.toString())
        println()
        println("+++++++++++++++")
        println("GRAFO")
        for (i in 0 until graph.size)
            println(graph[i].toString())
        println("+++++++++++++++")
        println()

        println("ARESTA \tVALOR")
        for (i in 1 until this.V) {
            println(parent[i].toString() + " - " + i + "\t" + graph[i][parent[i]])
        }
    }

    fun prim(graph: MutableList<MutableList<Int>>): Int {

        val parent: MutableList<Int> = mutableListOf()

        for (i in 0 until this.V) {
            parent.add(-1)
        }

        val key: MutableList<Int> = mutableListOf()

        for (i in 0 until this.V) {
            key.add(-1)
        }

        val mstSet: MutableList<Boolean> = mutableListOf()

        for (i in 0 until this.V) {
            mstSet.add(false)
        }

        for (i in 0 until this.V) {
            key[i] = Int.MAX_VALUE
            mstSet[i] = false
        }

        key[0] = 0

        parent[0] = -1

        for (count in 0 until this.V - 1) {

            val u = minKey(key, mstSet)

            mstSet[u] = true

            for (v in 0 until this.V)
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u
                    key[v] = graph[u][v]
                }
        }

        printPrim(parent, graph)

        return getCost(parent, graph)
    }


    fun getCost(parent: MutableList<Int>, graph: MutableList<MutableList<Int>>): Int {
        var cost = 0

        for (i in 1 until this.V) {
            //println(parent[i].toString() + " - " + i + "\t" + graph[i][parent[i]])
            cost += graph[i][parent[i]]
        }
        return cost
    }

}