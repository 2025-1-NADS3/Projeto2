package br.com.example.fecapayplus.data.model;

import com.google.gson.annotations.SerializedName;

public class Transacao {
    @SerializedName("id_transacao")
    private int idTransacao;

    @SerializedName("ra_aluno")
    private int raAluno;

    @SerializedName("tipo_transacao")
    private String tipoTransacao;

    @SerializedName("valor")
    private String valor;

    @SerializedName("data_transacao")
    private String dataTransacao;

    public Transacao(int idTransacao, int raAluno, String tipoTransacao, String valor, String dataTransacao) {
        this.idTransacao = idTransacao;
        this.raAluno = raAluno;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.dataTransacao = dataTransacao;
    }

    // Getters
    public int getIdTransacao() { return idTransacao; }
    public int getRaAluno() { return raAluno; }
    public String getTipoTransacao() { return tipoTransacao; }
    public String getValor() { return valor; }
    public String getDataTransacao() { return dataTransacao; }
}