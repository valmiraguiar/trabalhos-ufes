package com.ufes.pss.trabalhofinal.model;

public class UsuarioImagemModel {

    private int idUsuarioImagem;
    private int idUsuarioSrc;
    private int idUsuarioDst;
    private String path;

    public UsuarioImagemModel(int idUsuarioImagem, int idUsuarioSrc, int idUsuarioDst, String path) {
        this.idUsuarioImagem = idUsuarioImagem;
        this.idUsuarioSrc = idUsuarioSrc;
        this.idUsuarioDst = idUsuarioDst;
        this.path = path;
    }

    public UsuarioImagemModel(int idUsuarioSrc, int idUsuarioDst, String path) {
        this.idUsuarioSrc = idUsuarioSrc;
        this.idUsuarioDst = idUsuarioDst;
        this.path = path;
    }

    public int getIdUsuarioImagem() {
        return idUsuarioImagem;
    }

    public int getIdUsuarioSrc() {
        return idUsuarioSrc;
    }

    public void setIdUsuarioSrc(int idUsuarioSrc) {
        this.idUsuarioSrc = idUsuarioSrc;
    }

    public int getIdUsuarioDst() {
        return idUsuarioDst;
    }

    public String getPath() {
        return path;
    }

}
