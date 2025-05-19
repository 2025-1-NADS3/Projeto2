package br.com.example.fecapayplus.data.model;

public class ItemCarrinho {
    private int produtoId;
    private String nome;
    private String preco;
    private int quantidade;
    private String imagemLink;
    private String categoria;

    private String loja;


    public ItemCarrinho(int produtoId, String nome, String preco, int quantidade, String imagemLink, String categoria, String loja) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagemLink = imagemLink;
        this.categoria = categoria;
        this.loja = loja;
    }

    public static ItemCarrinho fromProduto(Produto produto) {
        return new ItemCarrinho(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                1,
                produto.getImagem(),
                produto.getCategoria(),
                produto.getLoja()

        );
    }

    public int getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public String getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
    public String getImagemLink() { return imagemLink; }
    public String getCategoria() { return categoria; }
    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

}