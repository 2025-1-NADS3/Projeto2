package br.com.example.fecapayplus.data.api;

import br.com.example.fecapayplus.data.model.User;
import br.com.example.fecapayplus.ui.login.LoginResponse;
import br.com.example.fecapayplus.ui.userProfile.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;


//Serviço para lidar com a manipulação de dados entre o backend
public interface IApiService {

    //Rota para cadastro
    @POST("api/cadastro")
    Call<Void> registerUser(@Body User user);

    //Rota para acessar a aplicação
    @POST("api/entrar")
    Call<LoginResponse> loginUser(@Body User user);

    //Rota para alterar senha
    @PATCH("api/alterar-senha/{ra}")
    Call<Void> changePassowrd(@Path("ra") int ra, @Body ProfileResponse request);

    //Rota para deletar conta
    @DELETE("api/deletar/{ra}")
    Call<Void> deleteAccount(@Path("ra") int ra);

}
