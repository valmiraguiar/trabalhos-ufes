fun main(args: Array<String>) {
    val txt = FileUtils().lerArquivo(
        "entrada2.txt"
    ) // caminho do arquivo
    val grafo = Grafo(txt)
}