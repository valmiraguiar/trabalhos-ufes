package Model;

import java.util.ArrayList;

public class DependenteDAO {

    private ArrayList<Dependente> dependentes;

    public DependenteDAO() {
        dependentes = new ArrayList();
    }

    public void addDependente(Dependente dependente) {
        dependentes.add(dependente);
    }

    @Override
    public String toString() {
        String retorno = "";
        for (Dependente d : dependentes) {
            retorno += d.toString();
        }
        return retorno;
    }

}
