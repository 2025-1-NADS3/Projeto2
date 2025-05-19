package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.model.Transacao;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class TransacaoRepository {
    private final IApiService apiService;
    public TransacaoRepository() {
        this.apiService = RetrofitClient.getClient().create(IApiService.class);

    }

    public void getTransacoes(int ra, Callback<List<Transacao>> callback) {
        Call<List<Transacao>> call = apiService.getTransacoes(ra);
        call.enqueue(callback);
    }
}
