package br.com.example.fecapayplus.ui.login;

import retrofit2.Callback;

import br.com.example.fecapayplus.data.api.IApiService;
import br.com.example.fecapayplus.data.api.RetrofitClient;
import br.com.example.fecapayplus.data.model.User;


//Camada intermediária para abstrair as chamadas à API
public class LoginRepository {

    //Instância do API service onde há os endpoints da API
    private final IApiService apiService;

    //Construtor da classe
    public LoginRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    //Metodo que obtem as informações definidas por meio da classe User
    public void loginUser(User user, Callback<LoginResponse> callback){
        apiService.loginUser(user).enqueue(callback);
    }
}
