package br.com.vertisys.model;

import java.time.LocalDate;

public class Lote {
    private int id;
    private LocalDate dataSemeadura;
    private String status;
    private LocalDate dataColheitaEstimada;
    private int qtdBandejas;
    private double areaOcupada;
    private Cultura cultura;
    private Estante estante;

    public Lote(LocalDate dataSemeadura, String status, LocalDate dataColheitaEstimada, int qtdBandejas, double areaOcupada, Cultura cultura, Estante estante) {
        this.dataSemeadura = dataSemeadura;
        this.status = status;
        this.dataColheitaEstimada = dataColheitaEstimada;
        this.qtdBandejas = qtdBandejas;
        this.areaOcupada = areaOcupada;
        this.cultura = cultura;
        this.estante = estante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataSemeadura() {
        return dataSemeadura;
    }

    public void setDataSemeadura(LocalDate dataSemeadura) {
        this.dataSemeadura = dataSemeadura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataColheitaEstimada() {
        return dataColheitaEstimada;
    }

    public void setDataColheitaEstimada(LocalDate dataColheitaEstimada) {
        this.dataColheitaEstimada = dataColheitaEstimada;
    }

    public int getQtdBandejas() {
        return qtdBandejas;
    }

    public void setQtdBandejas(int qtdBandejas) {
        this.qtdBandejas = qtdBandejas;
    }

    public double getAreaOcupada() {
        return areaOcupada;
    }

    public void setAreaOcupada(double areaOcupada) {
        this.areaOcupada = areaOcupada;
    }

    public Cultura getCultura() {
        return cultura;
    }

    public void setCultura(Cultura cultura) {
        this.cultura = cultura;
    }

    public Estante getEstante() {
        return estante;
    }

    public void setEstante(Estante estante) {
        this.estante = estante;
    }
}