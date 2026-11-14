package br.com.vertisys.model;

import java.time.LocalDateTime;

public class LeituraHidroponica extends Leitura {
    private double ph;
    private double ec;

    public LeituraHidroponica(LocalDateTime dataHora, Sensor sensor, double ph, double ec) {
        super(dataHora, sensor);
        this.ph = ph;
        this.ec = ec;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getEc() {
        return ec;
    }

    public void setEc(double ec) {
        this.ec = ec;
    }
}