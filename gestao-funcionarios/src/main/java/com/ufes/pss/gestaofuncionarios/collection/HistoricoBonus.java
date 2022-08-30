package com.ufes.pss.gestaofuncionarios.collection;

import com.ufes.pss.gestaofuncionarios.model.Bonus;
import com.ufes.pss.gestaofuncionarios.model.BonusHistorico;
import java.util.ArrayList;

public class HistoricoBonus {
    
    private ArrayList<BonusHistorico> historico;
    
    public HistoricoBonus() {
        historico = new ArrayList<>();
    }
    
    public void addBonusHistorico(Bonus bonus, String cargo, double salarioBase) {
        BonusHistorico bh = new BonusHistorico(bonus.getNome(), bonus.getValor(), cargo, salarioBase);
        bh.setData(bonus.getData());
        historico.add(bh);
    }
    
    public ArrayList<BonusHistorico> getHistorico() {
        return historico;
    }
    
}
