package br.com.vertisys.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Colheita {
    private LocalDate data;
    private double pesoBiomassa;
    private double pesoDescarte;
    private Lote lote;

    public Colheita(LocalDate data, double pesoBiomassa, double pesoDescarte, Lote lote) {
        this.data = data;
        this.pesoBiomassa = pesoBiomassa;
        this.pesoDescarte = pesoDescarte;
        this.lote = lote;
    }

    public long calcularTempoCiclo() {
        return ChronoUnit.DAYS.between(lote.getDataSemeadura(), data);
    }

    public double calcularAproveitamento() {
        return ((pesoBiomassa - pesoDescarte) / pesoBiomassa) * 100;
    }

    public double calcularRendimentoArea() {
        return (pesoBiomassa - pesoDescarte) / lote.getAreaOcupada();
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPesoBiomassa() {
        return pesoBiomassa;
    }

    public void setPesoBiomassa(double pesoBiomassa) {
        this.pesoBiomassa = pesoBiomassa;
    }

    public double getPesoDescarte() {
        return pesoDescarte;
    }

    public void setPesoDescarte(double pesoDescarte) {
        this.pesoDescarte = pesoDescarte;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}