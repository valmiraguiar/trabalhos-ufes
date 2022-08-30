package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.ImagemComponente;

public class Memento {
    private ImagemComponente estado;

    public Memento(ImagemComponente estado) {
        this.estado = estado;
    }

    protected ImagemComponente getEstado() {
        return estado;
    }
}
