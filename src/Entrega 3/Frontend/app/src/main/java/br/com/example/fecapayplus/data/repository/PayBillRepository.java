package br.com.example.fecapayplus.data.repository;

import android.util.Log;

import br.com.example.fecapayplus.services.IApiService;
import br.com.example.fecapayplus.services.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

//Repositório para lidar com chamadas na API
public class PayBillRepository {
    private final IApiService apiService;

    public PayBillRepository() {
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }


    public void payBill(int boletoId, int ra, Callback<ResponseBody> callback) {
        if (boletoId <= 0 || ra <= 0) {
            Log.e("PayBillRepository", "Parâmetros inválidos: boletoId=" + boletoId + ", ra=" + ra);
            return;
        }

        Call<ResponseBody> call = apiService.pagarBoleto(boletoId, ra);
        call.enqueue(callback);
    }

}
