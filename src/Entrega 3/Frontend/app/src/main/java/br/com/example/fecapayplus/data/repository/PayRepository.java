package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.dtos.CompraRequestDTO;
import br.com.example.fecapayplus.data.dtos.ProdutoCompraDTO;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayRepository {
    private final IApiService apiService;

    public PayRepository() {
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    public Call<ResponseBody> realizarPagamento(int ra, CompraRequestDTO compra) {
        return apiService.realizarPagamento(ra, compra);
    }
}
