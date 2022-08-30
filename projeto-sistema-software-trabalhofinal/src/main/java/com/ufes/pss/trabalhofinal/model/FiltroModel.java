package com.ufes.pss.trabalhofinal.model;

public class FiltroModel {
    
    private int idFiltro;
    private int idUsuario;
    private int idImagem;
    private String data;

    public FiltroModel(int idFiltro, int idUsuario, int idImagem, String data) {
        this.idFiltro = idFiltro;
        this.idUsuario = idUsuario;
        this.idImagem = idImagem;
        this.data = data;
    }

    public int getIdFiltro() {
        return idFiltro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public String getData() {
        return data;
    }

    public void setIdFiltro(int idFiltro) {
        this.idFiltro = idFiltro;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    public void setData(String data) {
        this.data = data;
    }
}
