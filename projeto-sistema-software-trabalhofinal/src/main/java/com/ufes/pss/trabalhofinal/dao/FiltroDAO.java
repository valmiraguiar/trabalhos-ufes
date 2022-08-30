package com.ufes.pss.trabalhofinal.dao;

import com.ufes.pss.trabalhofinal.conexao.ConexaoSQLite;
import com.ufes.pss.trabalhofinal.model.FiltroModel;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class FiltroDAO {
    
    public void criarTabelaFiltro() {
        String query = "CREATE TABLE IF NOT EXISTS filtro (" 
                + "idFiltro INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUsuario INTEGER REFERENCES usuario (idUsuario) ON UPDATE CASCADE, "
                + "data DATE NOT NULL, "
                + "idImagem INTEGER REFERENCES imagem (idImagem) ON UPDATE CASCADE "
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
    
    public void insertFiltro(UsuarioModel usuario, int idImagem) {
        var query = "INSERT INTO "
                + "filtro(idUsuario, data, idImagem) "
                + "VALUES (?, ?, ?)";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, usuario.getIdUsuario());
            pstmt.setString(2, LocalDate.now().toString());
            pstmt.setInt(3, idImagem);
            
            pstmt.executeUpdate();

            pstmt.close();

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar histórico de filtro: " + e.getMessage());
        }
    }
    
    public ArrayList<FiltroModel> getFiltros(int id) {
        String query = "SELECT * FROM filtro WHERE idImagem = ?";
        ArrayList<FiltroModel> filtros = new ArrayList<>();
        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idFiltro = rs.getInt("idFiltro");
                int idUsuario = rs.getInt("idUsuario");
                int idImagem = rs.getInt("idImagem");
                String data = rs.getString("data");

                filtros.add(new FiltroModel(idFiltro, idUsuario, idImagem, data));
            }

            rs.close();
            pstmt.close();
            conn.close();

            return filtros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao resgatar histórico de filtros: " + e.getMessage());
        }
    }
}
