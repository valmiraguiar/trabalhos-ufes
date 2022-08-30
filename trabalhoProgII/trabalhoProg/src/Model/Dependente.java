package Model;

public class Dependente extends Pessoa {

    private String parentesco;

    public Dependente(){
        
    }
    
    public Dependente(String nome, String parentesco) {
        this.nome = nome;
        this.parentesco = parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getParentesco() {
        return this.parentesco;
    }

    @Override
    public String toString() {

        return nome + " - " + parentesco + '\n';
    }
}
