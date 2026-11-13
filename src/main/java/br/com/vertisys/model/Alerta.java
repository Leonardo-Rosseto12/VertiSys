package br.com.vertisys.model;

import java.time.LocalDateTime;

public class Alerta {

    private int id;
    private String criticidade;
    private String parametro;
    private String descricao;
    private LocalDateTime dataHora;
    private Leitura leitura;

    public Alerta(String criticidade, String parametro, String descricao, LocalDateTime dataHora, Leitura leitura) {
        this.criticidade = criticidade;
        this.parametro = parametro;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.leitura = leitura;
    }

    public int getId() {
        return id;
    }

    public String getCriticidade() {
        return criticidade;
    }

    public String getParametro() {
        return parametro;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Leitura getLeitura() {
        return leitura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setLeitura(Leitura leitura) {
        this.leitura = leitura;
    }
}