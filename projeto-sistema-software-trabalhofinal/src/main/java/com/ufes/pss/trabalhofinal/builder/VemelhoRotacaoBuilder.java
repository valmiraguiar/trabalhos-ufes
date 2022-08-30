package com.ufes.pss.trabalhofinal.builder;

import com.pss.imagem.processamento.decorator.ImagemComponente;

public class VemelhoRotacaoBuilder implements Builder{
    
    private ImagemComponente imagem;

    @Override
    public void setImagem(ImagemComponente imagem) {
        this.imagem = imagem;
    }

    @Override
    public ImagemComponente alteraCor(ImagemComponente corDecorator) {
        return corDecorator;
    }

    @Override
    public ImagemComponente aplicaEfeito(ImagemComponente efeitoDecorator) {
        return efeitoDecorator;
    }
    
    @Override
    public ImagemComponente getResult() {
        return imagem;
    }
    
    @Override
    public String toString() {
        return "Vermelho + Rotacao";
    }
}
