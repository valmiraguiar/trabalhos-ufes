package modelDAO;

import controller.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Medico;

public class MedicoDAO {
    
    public List<Medico> listMedicos(){
        try{
            Connection con = DBConnection.connection();
        
            String sql = "SELECT mde.id_medicoespecialista as id_medico, ps.nome as nome, esp.descricao as especialidade FROM " +
                        "especialidade esp JOIN " +
                        "medicoespecialista mde ON mde.id_especialidade = esp.id_especialidade JOIN " +
                        "funcionario fc ON mde.id_medicoespecialista = fc.id_funcionario JOIN " +
                        "pessoa ps ON fc.id_funcionario = ps.id_pessoa;";
        
            Statement stmt = con.createStatement();
            
            ResultSet result = stmt.executeQuery(sql);
            
            List<Medico> medicos = new ArrayList<>();
            
            while(result.next()){
                Medico m = new Medico();
                m.setId_medico(result.getInt("id_medico"));
                m.setNome(result.getString("nome"));
                m.setEspecialidade(result.getString("especialidade"));
                
                medicos.add(m);
            }
            System.out.println(medicos.size());
            con.close();
            if(medicos != null)
                return medicos;
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
        return null;
    }
    
}
