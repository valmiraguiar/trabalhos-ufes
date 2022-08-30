package Model;

public abstract class Pessoa {

    protected String nome;


    public Pessoa(){
        
    }
    
    public Pessoa(String nome){
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }


}
