package br.com.example.fecapayplus.data.repository;

import java.util.List;

import br.com.example.fecapayplus.data.model.Boleto;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

//Repositorio para comunicar com a API
public class BoletoRepository {
    private final IApiService apiService;

    public BoletoRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }
    public void getBoletos(int ra, Callback<List<Boleto>> callback){
        Call<List<Boleto>> call = apiService.getBoletos(ra);
        call.enqueue(callback);
    }

}
