package br.com.example.fecapayplus.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


//Classe boleto
public class Boleto {
    @SerializedName("boleto_id")
    private int id;
    @SerializedName("valor")
    private double valor;
    @SerializedName("data_boleto")
    private Date data_boleto;
    @SerializedName("vencimento")
    private Date data_vencimento;
    @SerializedName("codigo")
    private int codigo;
    @SerializedName("status")
    private String status;

    public Boleto(int id, double valor, Date data_boleto, Date data_vencimento, int codigo, String status) {
        this.id = id;
        this.valor = valor;
        this.data_boleto = data_boleto;
        this.data_vencimento = data_vencimento;
        this.codigo = codigo;
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public double getValor() {
        return valor;
    }


    public Date getData_boleto() {
        return data_boleto;
    }


    public Date getData_vencimento() {
        return data_vencimento;
    }


    public int getCodigo() {
        return codigo;
    }

    public String getStatus() {
        return status;
    }

}
