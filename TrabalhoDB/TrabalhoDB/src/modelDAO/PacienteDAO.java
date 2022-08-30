package modelDAO;

import controller.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Paciente;

public class PacienteDAO {
    
    public void insertPaciente(Paciente p, int id_endereco) {
        try{
            Connection con = DBConnection.connection();
            
            
            //insere em pessoa
            String sqlPessoa = "INSERT INTO pessoa(cpf, telefone, nome, data_nascimento, sexo, id_endereco)" +
                            "VALUES(?, ?, ?, ?, ?, ?);";
            
            PreparedStatement stmt = con.prepareStatement(sqlPessoa);
            
            stmt.setString(1, p.getCpf());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getNomePaciente());
            stmt.setString(4, p.getDataNascimento());
            stmt.setString(5, p.getSexo());
            stmt.setInt(6, id_endereco);
            
            stmt.executeUpdate();
            
            //---------------
            
            //----pega a pessoa adicionada
            
            String sqlPegarPessoa = "SELECT id_pessoa FROM pessoa WHERE ("
                    + "nome = ? AND "
                    + "cpf = ? AND "
                    + "telefone = ? AND "
                    + "data_nascimento = ? AND "
                    + "id_endereco = ?"
                    + ")";
            
            PreparedStatement stmtPegarPessoa = con.prepareStatement(sqlPegarPessoa);
            
            stmtPegarPessoa.setString(1, p.getNomePaciente());
            stmtPegarPessoa.setString(2, p.getCpf());
            stmtPegarPessoa.setString(3, p.getTelefone());
            stmtPegarPessoa.setString(4, p.getDataNascimento());
            stmtPegarPessoa.setInt(5, id_endereco);
            
            
            ResultSet result = stmtPegarPessoa.executeQuery();
            
            result.next();
            int id_pessoa = result.getInt("id_pessoa");
            //-------------
            
            String sqlPaciente = "INSERT INTO paciente (id_paciente, carta_sus) "
                    + "VALUES(?, ?);";
            
            PreparedStatement stmtPaciente = con.prepareStatement(sqlPaciente);
            stmtPaciente.setInt(1, id_pessoa);
            stmtPaciente.setString(2, p.getCartaoSus());    
            
            stmtPaciente.executeUpdate();
            
            con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
    public void updateOrIgnorePaciente(Paciente p){
        try{
            Connection con = DBConnection.connection();
            
            
            //insere em pessoa
            String sqlPessoa = "INSERT INTO pessoa (id_pessoa, cpf, telefone, nome, data_nascimento, sexo, id_endereco) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                                "ON CONFLICT ON CONSTRAINT pessoa_pkey " +
                                "DO UPDATE SET " +
                                "cpf = EXCLUDED.cpf, " +
                                "telefone = EXCLUDED.telefone,  " +
                                "nome = EXCLUDED.nome,  " +
                                "data_nascimento = EXCLUDED.data_nascimento, " +
                                "sexo = EXCLUDED.sexo;";
            
            PreparedStatement stmt = con.prepareStatement(sqlPessoa);
            
            stmt.setInt(1, p.getIdPaciente());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getTelefone());
            stmt.setString(4, p.getNomePaciente());
            stmt.setString(5, p.getDataNascimento());
            stmt.setString(6, p.getSexo());
            stmt.setInt(7, p.getId_endereco());
            
            stmt.executeUpdate();
            
            String sqlPaciente = "INSERT INTO paciente (id_paciente, carta_sus) " +
                                    "VALUES (?, ?) " +
                                    "ON CONFLICT ON CONSTRAINT paciente_pkey " +
                                    "DO UPDATE SET " +
                                    "carta_sus = EXCLUDED.carta_sus;";
            
            PreparedStatement stmt2 = con.prepareStatement(sqlPaciente);
            
            stmt2.setInt(1, p.getIdPaciente());
            stmt2.setString(2, p.getCartaoSus());
            
            stmt2.executeUpdate();
                        
            con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
    public Paciente getPaciente(String cartao_sus){
        try{
            Connection con = DBConnection.connection();
        
            String sql = "SELECT * "
                    + "FROM pessoa ps JOIN paciente pc ON ps.id_pessoa = pc.id_paciente "
                    + "WHERE (carta_sus = ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, cartao_sus);
            
            ResultSet result = stmt.executeQuery();
            
            Paciente p = new Paciente();
            result.next();
            p.setIdPaciente(result.getInt("id_pessoa"));
            p.setDataNascimento(result.getString("data_nascimento"));
            p.setSexo(result.getString("sexo"));
            p.setNomePaciente(result.getString("nome"));
            p.setId_endereco(result.getInt("id_endereco"));
            p.setCpf(result.getString("cpf"));
            p.setCartaoSus(result.getString("carta_sus"));
            p.setTelefone(result.getString("telefone"));
            p.setSexo(result.getString("sexo"));
            
            con.close();
            return p;
        }catch(Exception e){
            System.out.println("Error "+e);
        }
        return null;
    }
    
    public int getIdPaciente(String cartao_sus){
        try{
            Connection con = DBConnection.connection();
        
            String sql = "SELECT id_pessoa FROM pessoa ps JOIN paciente pc ON ps.id_pessoa = pc.id_paciente "
                        + "WHERE (carta_sus = ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, cartao_sus);
            
            ResultSet result = stmt.executeQuery();
            
            result.next();
            int id_paciente = result.getInt("id_pessoa");
            
            con.close();
            return id_paciente;
        }catch(Exception e){
            System.out.println("Error "+e);
        }
        return -1;
    }
    
}
