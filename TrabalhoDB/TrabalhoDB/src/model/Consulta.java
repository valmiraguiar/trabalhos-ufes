
package model;

public class Consulta {

    private int id_consulta;
    private int id_agendamento;
    private String nomePaciente;
    private int id_paciente;
    private String dataNascimentoPaciente;
    private int id_medicoespecialista;    
    private String cartao_sus;

    public String getCartao_sus() {
        return cartao_sus;
    }

    public void setCartao_sus(String cartao_sus) {
        this.cartao_sus = cartao_sus;
    }
    
    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
    
    public int getId_medicoespecialista() {
        return id_medicoespecialista;
    }

    public void setId_medicoespecialista(int id_medicoespecialista) {
        this.id_medicoespecialista = id_medicoespecialista;
    }
    
    private String sexo;
    private String nomeMedico;
    private String dataConsulta;    
    private String especialidadeMedico;
    private String dataAgendamento;

    public int getId_agendamento() {
        return id_agendamento;
    }

    public void setId_agendamento(int id_agendamento) {
        this.id_agendamento = id_agendamento;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getDataNascimentoPaciente() {
        return dataNascimentoPaciente;
    }

    public void setDataNascimentoPaciente(String dataNascimentoPaciente) {
        this.dataNascimentoPaciente = dataNascimentoPaciente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getEspecialidadeMedico() {
        return especialidadeMedico;
    }

    public void setEspecialidadeMedico(String especialidadeMedico) {
        this.especialidadeMedico = especialidadeMedico;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    
    
    
}
