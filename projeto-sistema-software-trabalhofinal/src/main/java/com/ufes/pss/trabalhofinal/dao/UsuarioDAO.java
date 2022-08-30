package com.ufes.pss.trabalhofinal.dao;

import com.ufes.pss.trabalhofinal.conexao.ConexaoSQLite;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {

    public void criarTabelaUsuario() {
        String query = "CREATE TABLE IF NOT EXISTS usuario("
                + "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome VARCHAR NOT NULL, "
                + "usuarioNome VARCHAR NOT NULL UNIQUE, "
                + "senha VARCHAR NOT NULL, "
                + "administrador INT DEFAULT 0, "
                + "autorizado INT DEFAULT 0 ,"
                + "permissaoSolicitada INT DEFAULT 0 "
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

    public void insertUsuario(UsuarioModel usuario, boolean autorizado, boolean administrador) {
        String query = "INSERT INTO "
                + "usuario(nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String query_primeiro = "SELECT COUNT(idUsuario) as qtd FROM usuario";

        try {
            Connection conn = ConexaoSQLite.conectar();

            PreparedStatement pstmt = conn.prepareStatement(query_primeiro);

            ResultSet rs = pstmt.executeQuery();

            UsuarioModel usuarioResult = null;

            int qtdUsuarios = 0;
            if (rs.next()) {
                qtdUsuarios = rs.getInt("qtd");
            }

            if (qtdUsuarios > 0) {
                pstmt = conn.prepareStatement(query);

                pstmt.setString(1, usuario.getNome());
                pstmt.setString(2, usuario.getNomeUsuario());
                pstmt.setString(3, usuario.getSenha());
                pstmt.setInt(4, administrador ? 1 : 0);
                pstmt.setInt(5, 0);
                pstmt.setInt(6, 0);

                pstmt.executeUpdate();
            } else {
                pstmt = conn.prepareStatement(query);

                pstmt.setString(1, usuario.getNome());
                pstmt.setString(2, usuario.getNomeUsuario());
                pstmt.setString(3, usuario.getSenha());
                pstmt.setInt(4, 1);
                pstmt.setInt(5, 1);
                pstmt.setInt(6, 0);

                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    public UsuarioModel login(String usuarioNome, String senha) {
        var query = "SELECT * FROM usuario WHERE LOWER(usuarioNome) = LOWER(?) AND senha = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, usuarioNome);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            UsuarioModel usuario = null;

            if (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nome = rs.getString("nome");
                usuarioNome = rs.getString("usuarioNome");
                senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuario = new UsuarioModel(id, nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada);
                rs.close();
                pstmt.close();
                conn.close();
            } else {
                throw new RuntimeException(
                        "Não existe usuário com esse nome de usuário e senha");
            }

            rs.close();
            pstmt.close();
            conn.close();

            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro de login: " + e.getMessage());
        }
    }

    public UsuarioModel getUsuarioById(int id) {
        String query = "SELECT * FROM usuario WHERE idUsuario = ?";

        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            UsuarioModel usuario = null;

            if (rs.next()) {
                String nome = rs.getString("nome");
                String usuarioNome = rs.getString("usuarioNome");
                String senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuario = new UsuarioModel(id, nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada);

            }

            rs.close();
            pstmt.close();
            conn.close();

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    public ArrayList<UsuarioModel> getUsuarioByNome(String nome) {
        String query = "SELECT * FROM usuario WHERE nome LIKE ? ORDER BY nome";
        ArrayList<UsuarioModel> usuarios = new ArrayList<>();
        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nome);

            ResultSet rs = pstmt.executeQuery();
            UsuarioModel usuario = null;

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String usuarioNome = rs.getString("usuarioNome");
                String senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuarios.add(new UsuarioModel(id, nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada));

            }

            rs.close();
            pstmt.close();
            conn.close();

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    public ArrayList<UsuarioModel> getUsuarioByNomeUsuario(String nomeUsuario) {
        String query = "SELECT * FROM usuario WHERE usuarioNome LIKE ? ORDER BY nome";
        ArrayList<UsuarioModel> usuarios = new ArrayList<>();

        try {

            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nomeUsuario);

            ResultSet rs = pstmt.executeQuery();
            UsuarioModel usuario = null;

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean acessoPermitido = rs.getInt("acessopermitido") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuarios.add(new UsuarioModel(id, nome, nomeUsuario, senha, administrador, autorizado, permissaoSolicitada));

            }

            rs.close();
            pstmt.close();
            conn.close();

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    public ArrayList<UsuarioModel> getUsuarios() {
        ArrayList<UsuarioModel> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuario";

        try {
            Connection conn = ConexaoSQLite.conectar();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nome = rs.getString("nome");
                String usuarioNome = rs.getString("usuarioNome");
                String senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuarios.add(new UsuarioModel(id, nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada));
            }

            rs.close();
            stmt.close();
            conn.close();

            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os funcionarios: " + e.getMessage());
        }

    }

    public ArrayList<UsuarioModel> getTodosUsuariosPedindoPermissao() {
        ArrayList<UsuarioModel> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuario WHERE permissaoSolicitada = 1";

        try {
            Connection conn = ConexaoSQLite.conectar();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nome = rs.getString("nome");
                String usuarioNome = rs.getString("usuarioNome");
                String senha = rs.getString("senha");
                boolean administrador = rs.getInt("administrador") == 1;
                boolean autorizado = rs.getInt("autorizado") == 1;
                boolean permissaoSolicitada = rs.getInt("permissaoSolicitada") == 1;

                usuarios.add(new UsuarioModel(id, nome, usuarioNome, senha, administrador, autorizado, permissaoSolicitada));
            }

            rs.close();
            stmt.close();
            conn.close();

            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os funcionarios: " + e.getMessage());
        }

    }

    public void update(UsuarioModel usuario) {
        var query = "UPDATE usuario SET "
                + "nome = ?, "
                + "usuarioNome = ?, "
                + "senha = ?, "
                + "administrador = ?, "
                + "autorizado = ? ,"
                + "permissaoSolicitada = ? "
                + "WHERE idUsuario = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getNomeUsuario());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setInt(4, usuario.isAdministrador() ? 1 : 0);
            pstmt.setInt(5, usuario.isAutorizado() ? 1 : 0);
            pstmt.setInt(6, usuario.isPermissaoSolicitada()? 1 : 0);
            pstmt.setInt(7, usuario.getIdUsuario());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void excluirUsuario(int idUsuario) {
        String query = "DELETE FROM usuario WHERE idUsuario = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idUsuario);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage());
        }

    }
}
