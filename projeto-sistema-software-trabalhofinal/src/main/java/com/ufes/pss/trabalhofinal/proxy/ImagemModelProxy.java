package com.ufes.pss.trabalhofinal.proxy;

public class ImagemModelProxy implements IProxyImagem {
    
    private ImagemModel imagemReal;
    
    public ImagemModelProxy(int idUsuario, String imagem) {
        imagemReal = new ImagemModel(idUsuario, imagem);
    }

    @Override
    public int getIdImagem() {
        return imagemReal.getIdImagem();
    }

    @Override
    public String getImagem() {
        return imagemReal.getImagem();
    }

    @Override
    public void setIdImagem(int idImagem) {
        imagemReal.setIdImagem(idImagem);
    }

    @Override
    public void setImagem(String imagem) {
        imagemReal.setImagem(imagem);
    }
}
