package br.com.example.fecapayplus.data.model;

import com.google.gson.annotations.SerializedName;

public class Produto {
    @SerializedName("id")
    private int id;
    @SerializedName("nome")
    private String nome;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("estoque")
    private int estoque;
    @SerializedName("preco")
    private String preco;
    @SerializedName("imagem_link")
    private String imagemLink;

    @SerializedName("loja")
    private String loja;
    @SerializedName("categoria")
    private String categoria;

    public Produto(int id, String nome, String descricao, String preco, String imagem, String loja, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemLink = imagem;
        this.loja = loja;
        this.categoria = categoria;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPreco() {
        return preco;
    }

    public int getEstoque(){
        return estoque;
    }

    public String getImagem() {
        return imagemLink;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setImagemLink(String imagemLink) {
        this.imagemLink = imagemLink;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
