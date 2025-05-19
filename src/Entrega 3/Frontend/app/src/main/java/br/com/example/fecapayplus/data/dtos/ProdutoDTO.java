package br.com.example.fecapayplus.data.dtos;

import java.util.List;

import br.com.example.fecapayplus.data.model.Boleto;
import br.com.example.fecapayplus.data.model.Produto;

public class ProdutoDTO {
    private List<Produto> produtos;
    public ProdutoDTO(List<Produto> produtos){
        this.produtos = produtos;
    }
    public List<Produto> getProdutos() {
        return produtos;
    }

}
