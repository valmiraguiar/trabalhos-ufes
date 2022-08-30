package com.ufes.pss.trabalhofinal.builder;

import com.pss.imagem.processamento.decorator.AzulDecorator;
import com.pss.imagem.processamento.decorator.EspelhadaDecorator;
import com.pss.imagem.processamento.decorator.ImagemComponente;
import com.pss.imagem.processamento.decorator.RotacionaDecorator;
import com.pss.imagem.processamento.decorator.VerdeDecorator;
import com.pss.imagem.processamento.decorator.VermelhoDecorator;

public class Diretor {
    
    public void fazVerdeEspelhada(Builder builder, ImagemComponente imagem) throws InterruptedException {
        
        builder.setImagem(
                builder.alteraCor(new VerdeDecorator(
                        builder.aplicaEfeito(new EspelhadaDecorator(imagem)))));
    }
    
    public void fazVermelhoRotacao(Builder builder, ImagemComponente imagem) throws InterruptedException {
        builder.setImagem(
                builder.alteraCor(new VermelhoDecorator(
                        builder.aplicaEfeito(new RotacionaDecorator(imagem, 90)))));
    }
    
    public void fazAzulEspelhada(Builder builder, ImagemComponente imagem) throws InterruptedException {
        builder.setImagem(
                builder.alteraCor(new AzulDecorator(
                        builder.aplicaEfeito(new EspelhadaDecorator(imagem)))));
    }
}
