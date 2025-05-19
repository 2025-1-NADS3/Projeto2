package br.com.example.fecapayplus.data.repository;

import br.com.example.fecapayplus.data.dtos.UserBalanceDTO;
import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import retrofit2.Call;

public class BalanceRepository {
    private final IApiService apiService;

    public BalanceRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }
    public Call<UserBalanceDTO> addBalance(int ra, UserBalanceDTO userBalance) {
        return apiService.setSaldo(ra, userBalance);
    }

}
