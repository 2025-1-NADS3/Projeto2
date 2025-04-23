package br.com.example.fecapayplus.data.repository;

import br.com.example.fecapayplus.data.dtos.UserBalanceDTO;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

//Repositório para comunicar com a API
public class HomeRepository {
    private final IApiService apiService;

    public HomeRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    public void getSaldo(int ra, Callback<UserBalanceDTO>callback){
        Call<UserBalanceDTO> call = apiService.getSaldo(ra);
        call.enqueue(callback);
    }
}
