package com.ufes.pss.trabalhofinal.dao;

import com.ufes.pss.trabalhofinal.conexao.ConexaoSQLite;
import com.ufes.pss.trabalhofinal.model.UsuarioImagemModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioImagemDAO {

    public void criarTabelaImagem() {
        String query = "CREATE TABLE IF NOT EXISTS usuarioImagem ("
                + "idUsuarioImagem INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUsuarioSrc INTEGER NOT NULL, "
                + "idUsuarioDst INTEGER NOT NULL, "
                + "pathImg VARCHAR NOT NULL"
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

    public void insertUsuarioImg(int idUsuarioSrc, int idUsuarioDst, String pathImg) {
        String query = "INSERT INTO "
                + "usuarioImagem(idUsuarioSrc, idUsuarioDst, pathImg) "
                + "VALUES (?, ?, ?)";
        try {
            Connection conn = ConexaoSQLite.conectar();

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idUsuarioSrc);
            pstmt.setInt(2, idUsuarioDst);
            pstmt.setString(3, pathImg);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    public ArrayList<UsuarioImagemModel> getUsuarioImgs(int idUsuario) {
        String query = "SELECT * FROM usuarioImagem WHERE idUsuarioDst = ?";

        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idUsuario);

            ResultSet rs = pstmt.executeQuery();
            
            ArrayList<UsuarioImagemModel> usuarioImgs = new ArrayList<>();

            while (rs.next()) {
                int idUsuarioImagem = rs.getInt("idUsuarioImagem");
                int idUsuarioSrc = rs.getInt("idUsuarioSrc");
                int idUsuarioDst = rs.getInt("idUsuarioDst");
                String path = rs.getString("pathImg");

                usuarioImgs.add(new UsuarioImagemModel(idUsuarioImagem, idUsuarioSrc, idUsuarioDst, path));
            }

            rs.close();
            pstmt.close();
            conn.close();

            return usuarioImgs;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar imagens enviadas ao usuario!\n " + e.getMessage());
        }
    }
    
    public void excluirById(int idUsuarioImagem) {
        String query = "DELETE FROM usuarioImagem WHERE idUsuarioImagem = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idUsuarioImagem);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir envio de imagem ao usuário!\n " + e.getMessage());
        }
    }
}
