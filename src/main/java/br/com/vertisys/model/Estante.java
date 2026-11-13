package br.com.vertisys.model;

import java.util.ArrayList;

public class Estante {
    private int id;
    private String localizacao;
    private String status;
    private ArrayList<Lote> lotes;

    public Estante(String localizacao, String status) {
        this.localizacao = localizacao;
        this.status = status;
        this.lotes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(ArrayList<Lote> lotes) {
        this.lotes = lotes;
    }
}