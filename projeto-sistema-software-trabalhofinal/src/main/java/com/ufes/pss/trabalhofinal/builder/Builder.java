package com.ufes.pss.trabalhofinal.builder;

import com.pss.imagem.processamento.decorator.ImagemComponente;

public interface Builder {
    
    public void setImagem(ImagemComponente imagem);
    public ImagemComponente alteraCor(ImagemComponente corDecorator);
    public ImagemComponente aplicaEfeito(ImagemComponente efeitoDecorator);
    public ImagemComponente getResult();
}
