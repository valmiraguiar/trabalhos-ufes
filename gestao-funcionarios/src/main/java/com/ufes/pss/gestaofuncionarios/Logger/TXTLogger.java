package com.ufes.pss.gestaofuncionarios.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TXTLogger extends AbstractLogger {
   private File arquivo;

    public TXTLogger(String nomeArquivo) {
        super(nomeArquivo);

        this.arquivo = new File(nomeArquivo);
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void logCRUDFuncionario(String nome, String operacao) {
        try {
            String log = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()) + " Funcionário: "
                    + nome + " [" + operacao + "].\n";
            Files.write(Paths.get(this.getNomeArquivo()), log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer log");
        }
    }

    @Override
    public void logFalha(String operacao, String excesao) {
        try {
            String log = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()) + "Falha ao realizar operação de: "
                    + operacao + " Exceção: ";
            Files.write(Paths.get(this.getNomeArquivo()), log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer log");
        }
    }

    @Override
    public void logConsultaBonus(String nome) {
        try {
            String log = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()) + " Bônus consultado de: "
                    + nome + ".\n";
            Files.write(Paths.get(this.getNomeArquivo()), log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer log");
        }
    }

    @Override
    public void logCalculaSalario(ArrayList<String> funcionariosCalculados) {
        try {
            if (funcionariosCalculados.size() > 0) {
                String info = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()) + " Salário calculado para o(s) funcionário(s): [\n";
                StringBuilder sb = new StringBuilder();
                sb.append(info);
                for (String nome : funcionariosCalculados) {
                    sb.append("\t").append(nome).append("\n");
                }
                sb.append("]\n");

                String log = sb.toString();

                Files.write(Paths.get(this.getNomeArquivo()), log.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer log");
        }
    }

}
