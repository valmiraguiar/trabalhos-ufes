fun main(args: Array<String>) {
    //NOME DO ARQUIVO EXEMPLO: entrada1.txt
    val txt = FilesUtils().lerArquivo(
        "C:/Users/Valmir Aguiar/Documents/ufes/algoritmos-grafos/atividade2/impl/atividade2-grafos/entrada2.txt"
    ) // caminho do arquivo

    val grafo = Grafo(txt)
}