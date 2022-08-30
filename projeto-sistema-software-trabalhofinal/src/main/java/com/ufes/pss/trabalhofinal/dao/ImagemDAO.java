package com.ufes.pss.trabalhofinal.dao;

import com.ufes.pss.trabalhofinal.conexao.ConexaoSQLite;
import com.ufes.pss.trabalhofinal.proxy.IProxyImagem;
import com.ufes.pss.trabalhofinal.proxy.ImagemModelProxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImagemDAO {
    
    public void criarTabelaImagem() {
        String query = "CREATE TABLE IF NOT EXISTS imagem (" 
                + "idImagem INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "imagem VARCHAR"
                + ")";
           
        try {

            Connection conn = ConexaoSQLite.conectar();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();

        } catch (SQLException e) {
                throw new RuntimeException("Erro ao criar banco de dados: " + e.getMessage());
        }
    }
    
    public IProxyImagem getImagemByString(String imagem) {
        String query = "SELECT * FROM imagem WHERE imagem = ?";

        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, imagem);

            ResultSet rs = pstmt.executeQuery();
            ImagemModelProxy imagemModel = null;

            if (rs.next()) {
                int idImagem = rs.getInt("idImagem");
                String imagemString = rs.getString("imagem");
                
                imagemModel = new ImagemModelProxy(idImagem, imagemString);
            }

            rs.close();
            pstmt.close();
            conn.close();

            return imagemModel;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage());
        }
    }
}
