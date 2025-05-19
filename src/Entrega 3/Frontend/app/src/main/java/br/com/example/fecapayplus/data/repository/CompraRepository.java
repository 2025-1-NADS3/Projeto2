package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.model.Compra;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class CompraRepository {
    private final IApiService apiService;

    public CompraRepository() {
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    public void getCompras(int ra, Callback<List<Compra>> callback) {
        Call<List<Compra>> call = apiService.getCompras(ra);
        call.enqueue(callback);
    }

}
