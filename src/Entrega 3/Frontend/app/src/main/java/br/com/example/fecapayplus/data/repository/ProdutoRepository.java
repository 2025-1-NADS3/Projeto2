package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.dtos.ProdutoDTO;
import br.com.example.fecapayplus.data.model.Produto;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ProdutoRepository {
    private final IApiService apiService;
    public ProdutoRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }
    public void getProdutos(String loja, Callback<ProdutoDTO> callback){
        Call<ProdutoDTO> call = apiService.getProdutos(loja);
        call.enqueue(callback);
    }
}
