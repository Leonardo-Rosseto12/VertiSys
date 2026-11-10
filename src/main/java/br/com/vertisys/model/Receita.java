package br.com.vertisys.model;

public class Receita {
    private int idReceita;
    private double densidadeSemeadura;
    private double phMedio;
    private double ecMedio;
    private int cicloDias;
    private double tempArAlvo;
    private double fotoperiodo;

    public Receita(double densidadeSemeadura, double phMedio, double ecMedio, int cicloDias, double tempArAlvo, double fotoperiodo) {
        this.densidadeSemeadura = densidadeSemeadura;
        this.phMedio = phMedio;
        this.ecMedio = ecMedio;
        this.cicloDias = cicloDias;
        this.tempArAlvo = tempArAlvo;
        this.fotoperiodo = fotoperiodo;
    }

    public int getIdReceita() {
        return idReceita;
    }

    public double getFotoperiodo() {
        return fotoperiodo;
    }

    public double getTempArAlvo() {
        return tempArAlvo;
    }

    public int getCicloDias() {
        return cicloDias;
    }

    public double getEcMedio() {
        return ecMedio;
    }

    public double getPhMedio() {
        return phMedio;
    }

    public double getDensidadeSemeadura() {
        return densidadeSemeadura;
    }
    //--------------------------------------------------------------------------------
    public void setDensidadeSemeadura(double densidadeSemeadura) {
        this.densidadeSemeadura = densidadeSemeadura;
    }

    public void setPhMedio(double phMedio) {
        this.phMedio = phMedio;
    }

    public void setEcMedio(double ecMedio) {
        this.ecMedio = ecMedio;
    }

    public void setCicloDias(int cicloDias) {
        this.cicloDias = cicloDias;
    }

    public void setTempArAlvo(double tempArAlvo) {
        this.tempArAlvo = tempArAlvo;
    }

    public void setFotoperiodo(double fotoperiodo) {
        this.fotoperiodo = fotoperiodo;
    }
}
