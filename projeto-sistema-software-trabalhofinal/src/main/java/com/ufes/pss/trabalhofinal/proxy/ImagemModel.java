package com.ufes.pss.trabalhofinal.proxy;

public class ImagemModel implements IProxyImagem{
    
    private int idImagem;
    private String imagem;

    public ImagemModel(int idImagem, String imagem) {
        this.idImagem = idImagem;
        this.imagem = imagem;
    }

    @Override
    public int getIdImagem() {
        return idImagem;
    }

    @Override
    public String getImagem() {
        return imagem;
    }

    @Override
    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    @Override
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

