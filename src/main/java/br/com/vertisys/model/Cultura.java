package br.com.vertisys.model;

public class Cultura {
    private int idCultura;
    private String nomePopular;
    private String nomeCientifico;
    private Receita receita;

    public Cultura(String nomePopular, String nomeCientifico, Receita receita) {
        this.nomePopular = nomePopular;
        this.nomeCientifico = nomeCientifico;
        this.receita = receita;
    }

    public int getIdCultura() {
        return idCultura;
    }

    public String getNomePopular() {
        return nomePopular;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setIdCultura(int idCultura) {
        this.idCultura = idCultura;
    }

    public void setNomePopular(String nomePopular) {
        this.nomePopular = nomePopular;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}