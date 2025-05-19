package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.model.TransferenciaRequest;
import br.com.example.fecapayplus.data.model.TransferenciaResponse;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class FinancasRepository {
    private final IApiService apiService;

    public FinancasRepository() {
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    public void transferirSaldo(TransferenciaRequest request, Callback<TransferenciaResponse> callback) {
        Call<TransferenciaResponse> call = apiService.transferirSaldo(request);
        call.enqueue(callback);
    }
}