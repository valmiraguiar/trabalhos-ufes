package modelDAO;

import controller.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import model.Endereco;

public class EnderecoDAO {

    public int insertEndereco(Endereco endereco) {
        try{
            Connection con = DBConnection.connection();
            
            String sql = "INSERT INTO endereco(logradouro,cep,numero,bairro)" +
                            "VALUES(?, ?, ?, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            
            stmt.executeUpdate();
            
            String sqlPegarEndereco = "SELECT id_endereco FROM endereco WHERE ("
                    + "logradouro = ? AND "
                    + "cep = ? AND "
                    + "numero = ? AND "
                    + "bairro = ?"
                    + ")";
            PreparedStatement stmtPegarEndereco = con.prepareStatement(sqlPegarEndereco);
            
            stmtPegarEndereco.setString(1, endereco.getLogradouro());
            stmtPegarEndereco.setString(2, endereco.getCep());
            stmtPegarEndereco.setString(3, endereco.getNumero());
            stmtPegarEndereco.setString(4, endereco.getBairro());
            
            ResultSet result = stmtPegarEndereco.executeQuery();
            result.next();
            
            int id_endereco = result.getInt("id_endereco");
            con.close();
            return id_endereco;
        }catch(SQLException e){
            System.out.println("Error: "+e);
        }
        return -1;
    }
    
    public Endereco getEndereco(int id_endereco){
        try{
            Connection con = DBConnection.connection();
        
            String sql = "SELECT * FROM endereco "
                        + "WHERE (id_endereco = ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, id_endereco);
            
            ResultSet result = stmt.executeQuery();
            
            Endereco e = new Endereco();
            result.next();
            e.setId_endereco(id_endereco);
            e.setLogradouro(result.getString("logradouro"));
            e.setCep(result.getString("cep"));
            e.setNumero(result.getString("numero"));
            e.setBairro(result.getString("bairro"));
            
            con.close();
            return e;
        }catch(Exception e){
            System.out.println("Error "+e);
        }
        return null;
    }
    
    public void updateOrIgnoreEndereco(Endereco end){
        try{
            Connection con = DBConnection.connection();
           
            String sql = "INSERT INTO endereco (id_endereco, logradouro, cep, numero, bairro) " +
                                "VALUES (?, ?, ?, ?, ?) " +
                                "ON CONFLICT ON CONSTRAINT endereco_pkey " +
                                "DO UPDATE SET " +
                                "logradouro = EXCLUDED.logradouro, " +
                                "cep  = EXCLUDED.cep, " +
                                "numero = EXCLUDED.numero, " +
                                "bairro = EXCLUDED.bairro;";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, end.getId_endereco());
            stmt.setString(2, end.getLogradouro());
            stmt.setString(3, end.getCep());
            stmt.setString(4, end.getNumero());
            stmt.setString(5, end.getBairro());
            
            stmt.executeUpdate();
            
            con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
}
