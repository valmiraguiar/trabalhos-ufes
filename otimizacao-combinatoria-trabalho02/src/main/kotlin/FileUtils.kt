
import java.nio.file.Files
import java.nio.file.Paths

class FileUtils {
    fun lerArquivo(caminho: String): String {
        val path = Paths.get(caminho)
        return try {
            val txt = Files.readAllBytes(path)
            //char[] letras = leitura.toCharArray();

            //JOptionPane.showMessageDialog(null, leitura);
            String(txt)
        } catch (e: Exception) {
            println("Error->  ${e.message}")
            return ""
        }
    }

    fun prepararTexto(txt: String): String {
        return txt.replace(' ', '\n')
    }

}