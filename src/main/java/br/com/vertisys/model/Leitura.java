package br.com.vertisys.model;

import java.time.LocalDateTime;

public abstract class Leitura {
    private int id;
    private LocalDateTime dataHora;
    private Sensor sensor;

    public Leitura(LocalDateTime dataHora, Sensor sensor) {
        this.dataHora = dataHora;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}