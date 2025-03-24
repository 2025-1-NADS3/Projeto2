package br.com.example.fecapayplus.ui.userProfile;

import retrofit2.Callback;
import retrofit2.Call;


import br.com.example.fecapayplus.data.api.IApiService;
import br.com.example.fecapayplus.data.api.RetrofitClient;

//Camada intermediária para abstrair as chamadas à API
public class ProfileRepository {
    //Instância do API service onde há os endpoints da API
    private final IApiService apiService;

    //Construtor da classe
    public ProfileRepository(){
        this.apiService = RetrofitClient.getClient().create(IApiService.class);
    }

    //Construtor para lidar com a alteração de senha
    public void changePassword(Integer ra, String senhaAtual, String novaSenha, Callback<Void> callback){
        ProfileResponse response = new ProfileResponse(senhaAtual, novaSenha);
        Call<Void> call = apiService.changePassowrd(ra, response);
        call.enqueue(callback);
    }

    //Construtor para deletar a conta do usuário com base na RA
    public void deleteAccount(Integer ra, Callback<Void> callback){
        Call<Void> call = apiService.deleteAccount(ra);
        call.enqueue(callback);
    }
}
