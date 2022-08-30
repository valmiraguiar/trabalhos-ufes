data class Tarefa (
    val idTarefa: Int,
    val predecessores: List<Int>,
    val sucessores: List<Int>
)