package modelDAO;

import controller.DBConnection;
import java.sql.Connection;
import model.Agendamento;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.Consulta;
public class AgendamentoDAO {
    
    public Consulta insertAgendamento(Agendamento a){
        try{
             Connection con = DBConnection.connection();
             
             String sql = "INSERT INTO agendamento (data_consulta, data_agendamento, id_recepcionista, id_paciente) "
                     + "VALUES (?, ?, ?, ?);";
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setString(1, a.getData_consulta());
             stmt.setString(2, a.getData_agendamento());
             stmt.setInt(3, 11);
             stmt.setInt(4, a.getId_paciente());
             
             stmt.executeUpdate();
             
             //Consulta pra pegar o id_agendamento
             String sql2 = "SELECT id_agendamento FROM agendamento "
                     + "WHERE ("
                     + "data_consulta = ? AND "
                     + "data_agendamento = ? AND "
                     + "id_paciente = ?"
                     + ");";
             PreparedStatement stmt2 = con.prepareStatement(sql2);
             stmt2.setString(1, a.getData_consulta());
             stmt2.setString(2, a.getData_agendamento());
             stmt2.setInt(3, a.getId_paciente());
             
             ResultSet result = stmt2.executeQuery();
             result.next();
             Consulta c = new Consulta();
             c.setId_agendamento(result.getInt("id_agendamento"));
             
             con.close();
             return c;
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
        return null;
    }
    
    public void deleteAgendamento(int id_agendamento){
        try{
             Connection con = DBConnection.connection();
             
             String sql = "DELETE FROM agendamento WHERE id_agendamento = ?;";
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, id_agendamento);        
             
             stmt.executeUpdate();
             
             con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
}
