/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atvgrafosvalmir.atividade1.grafos.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valmir Aguiar
 */
public class FilesUtils {

    public String lerArquivo(String caminho) {
        Logger logger = Logger.getLogger("MyLog");
        Path path = Paths.get(caminho);
        try {
            byte[] txt = Files.readAllBytes(path);

            String leitura = new String(txt);
            //char[] letras = leitura.toCharArray();

            //JOptionPane.showMessageDialog(null, leitura);
            return leitura;
        } catch (Exception e) {
            logger.log(Level.INFO, "Exception: ", e.getMessage());
            return null;
        }
    }

    public String prepararTexto(String txt) {
        return txt.replace(' ', '\n');
    }
}
