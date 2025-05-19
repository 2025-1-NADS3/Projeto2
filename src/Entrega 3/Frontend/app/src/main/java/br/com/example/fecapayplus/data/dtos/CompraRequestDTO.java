package br.com.example.fecapayplus.data.dtos;

import java.util.List;

public class CompraRequestDTO {
    private List<ProdutoCompraDTO> itens;

    public CompraRequestDTO(List<ProdutoCompraDTO> itens) {
        this.itens = itens;
    }


}
