package com.ufes.pss.trabalhofinal.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:storage/usuario.sqlite";
    
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
