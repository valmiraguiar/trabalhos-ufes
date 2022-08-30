package com.ufes.pss.gestaofuncionarios.p1.descontoseimpostos.model;

public class Desconto {
    private String tipo;
    private double valor;

    public Desconto(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Desconto{" +
                "tipo='" + tipo + '\'' +
                ", valor=" + valor +
                '}';
    }
}
