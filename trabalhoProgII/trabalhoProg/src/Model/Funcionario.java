package Model;

public class Funcionario extends Pessoa {

    private int id;
    private String endereco;
    private String cargo;
    private String departamento;
    private String dataInicio;
    private double salario;
    private DependenteDAO dependentes;

    public Funcionario(int id, String nome, String endereco, String cargo, String departamento, String dataInicio, double salario) {
        super(nome);
        this.endereco = endereco;
        this.id = id;
        this.cargo = cargo;
        this.departamento = departamento;
        this.dataInicio = dataInicio;
        this.salario = salario;
        dependentes = new DependenteDAO();
    }

    public Funcionario(){
        dependentes = new DependenteDAO();
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setDependentes(DependenteDAO dependentes) {
        this.dependentes = dependentes;
    }

    // getters
    public int getId() {
        return this.id;
    }
    
    public String getEndereco(){
        return this.endereco;
    }

    public String getCargo() {
        return this.cargo;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public String getDataInicio() {
        return this.dataInicio;
    }

    public Double getSalario() {
        return this.salario;
    }

    public DependenteDAO getDependentes() {
        return dependentes;
    }

    @Override
    public String toString() {
        String s = " - ";
        return id + s + nome + s + endereco + s + cargo + s + departamento + s + dataInicio + s + "R$" + salario + '\n' + dependentes + '\n';
    }

}
