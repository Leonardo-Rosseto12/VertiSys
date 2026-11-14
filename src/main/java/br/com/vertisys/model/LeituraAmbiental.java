package br.com.vertisys.model;

import java.time.LocalDateTime;

public class LeituraAmbiental extends Leitura {

    private double tempAr;
    private double umidade;
    private double co2;

    public LeituraAmbiental(LocalDateTime dataHora, Sensor sensor, double tempAr, double umidade, double co2) {
        super(dataHora, sensor);
        this.tempAr = tempAr;
        this.umidade = umidade;
        this.co2 = co2;
    }

    public double getTempAr() {
        return tempAr;
    }

    public void setTempAr(double tempAr) {
        this.tempAr = tempAr;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }
}