package br.com.example.fecapayplus.data.model;

import com.google.gson.annotations.SerializedName;

public class Compra {
    private int id;
    private String data;
    private String loja;
    private int quantidade;
    private double preco;
    @SerializedName("nome_item")
    private String nomeItem;
    @SerializedName("imagem_link")
    private String imagemLink;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getImagemLink() {
        return imagemLink;
    }

    public void setImagemLink(String imagemLink) {
        this.imagemLink = imagemLink;
    }
}
