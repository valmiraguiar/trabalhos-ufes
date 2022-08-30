import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger

class FileUtils {
    fun lerArquivo(caminho: String): String {
        val logger = Logger.getLogger("MyLog")
        val path = Paths.get(caminho)
        return try {
            val txt = Files.readAllBytes(path)
            //char[] letras = leitura.toCharArray();

            //JOptionPane.showMessageDialog(null, leitura);
            String(txt)
        } catch (e: Exception) {
            logger.log(Level.INFO, "Exception: ", e.message)
            ""
        }
    }

    fun prepararTexto(txt: String): String {
        return txt.replace(' ', '\n')
    }
}