package model;

public class Paciente {

    private int idPaciente;
    private String nomePaciente;
    private String cpf;
    private String telefone;
    private String dataNascimento;
    private String cartaoSus;
    private String sexo;
    private int id_endereco;

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }
    
    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    @Override
    public String toString(){
        return "ID: #"+ this.idPaciente + "\n" +
                "NOME: "+ this.nomePaciente + "\n" +
                "SEXO: "+this.sexo + "\n" +
                "CPF: "+ this.cpf + "\n" +
                "TELEFONE: "+ this.telefone + "\n" +
                "DATA NASCIMENTO: "+ this.dataNascimento + "\n" +
                "CARTAO SUS: "+ this.cartaoSus;                
    }    
}
