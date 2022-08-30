package modelDAO;

import controller.DBConnection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Consulta;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ConsultaDAO {
    
    public List<Consulta> listConsultas(String cartao_sus){        
        try{
            Connection con = DBConnection.connection();
            
            String sql = "SELECT ps.id_pessoa as id_pessoa, "
                    + "ps.nome as nomePessoa, "
                    + "ps.data_nascimento as dataNascimento, "
                    + "ps.sexo as sexo, " +
"                      cs.id_consulta as id_consulta, "
                    + "ag.data_consulta as dataConsulta, "
                    + "pss.nome as nomeMedico, "
                    + "esp.descricao as especialidade, "
                    + "ag.data_agendamento as dataAgendamento " +
"                            FROM  " +
"                            pessoa ps JOIN  " +
"                            paciente pc ON ps.id_pessoa = pc.id_paciente JOIN  " +
"                            agendamento ag ON pc.id_paciente = ag.id_paciente JOIN  " +
"                            consulta cs ON ag.id_agendamento = cs.id_agendamento JOIN  " +
"                            medicoespecialista mde ON cs.id_medicoespecialista  = mde.id_medicoespecialista JOIN  " +
"                            funcionario fc ON mde.id_medicoespecialista = fc.id_funcionario JOIN" +
"                            especialidade esp ON mde.id_especialidade = esp.id_especialidade JOIN " +
"                            pessoa pss ON fc.id_funcionario = pss.id_pessoa " +
"                            WHERE pc.carta_sus = ? ;";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, cartao_sus);
            
            ResultSet result = stmt.executeQuery();
            
            ArrayList<Consulta> consultas = new ArrayList<>();
            while(result.next()){
                Consulta c = new Consulta();
                c.setId_consulta(result.getInt("id_consulta"));
                c.setNomePaciente(result.getString("nomePessoa"));
                c.setId_paciente(result.getInt("id_pessoa"));
                c.setDataNascimentoPaciente(result.getString("dataNascimento"));
                c.setSexo(result.getString("sexo"));
                c.setDataConsulta(result.getString("dataConsulta"));
                c.setNomeMedico(result.getString("nomeMedico"));
                c.setEspecialidadeMedico(result.getString("especialidade"));
                c.setDataAgendamento(result.getString("dataAgendamento"));
                
                consultas.add(c);
            }
            System.out.println(consultas.size());
            con.close();
            return consultas;
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
        return null;
    }
    
    public void insertConsulta(Consulta c){
        try{
             Connection con = DBConnection.connection();
             
             String sql = "INSERT INTO consulta (id_medicoespecialista, id_agendamento) "
                     + "VALUES (?, ?);";
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, c.getId_medicoespecialista());
             stmt.setInt(2, c.getId_agendamento());             
             
             stmt.executeUpdate();
             
             con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
    public void deleteConsulta(int id_consulta){
        try{
             Connection con = DBConnection.connection();
             
             String sql = "DELETE FROM consulta WHERE id_consulta = ? ;";
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, id_consulta);            
             
             stmt.executeUpdate();
             
             con.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
}
