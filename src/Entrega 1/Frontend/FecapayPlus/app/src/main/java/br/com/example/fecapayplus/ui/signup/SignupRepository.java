package br.com.example.fecapayplus.ui.signup;

import retrofit2.Callback;

import br.com.example.fecapayplus.data.api.IApiService;
import br.com.example.fecapayplus.data.api.RetrofitClient;
import br.com.example.fecapayplus.data.model.User;

//Camada intermediária para abstrair as chamadas à API
public class SignupRepository {

    //Instância do API service onde há os endpoints da API
    private final IApiService apiService;

    //Construtor da classe
    public SignupRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    //Metodo que obtem as informações definidas por meio da classe User
    public void registerUser(User user, Callback<Void> callback){
        apiService.registerUser(user).enqueue(callback);
    }
}
