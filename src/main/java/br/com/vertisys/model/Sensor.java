package br.com.vertisys.model;

public class Sensor {
    private int id;
    private String nome;
    private String localizacao;
    private String tipo;
    private int idLote;

    public Sensor(String nome, String localizacao, String tipo, int idLote) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.idLote = idLote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }
}