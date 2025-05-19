// ProdutoCompraDTO.java
package br.com.example.fecapayplus.data.dtos;

public class ProdutoCompraDTO {
    private int item_id;
    private int quantidade;

    private String loja;

    public ProdutoCompraDTO(int item_id, int quantidade, String loja) {
        this.item_id = item_id;
        this.quantidade = quantidade;
        this.loja = loja;
    }

    // Getters
    public int getItem_id() { return item_id; }
    public int getQuantidade() { return quantidade; }

    public String getLoja() { return loja; }
}
