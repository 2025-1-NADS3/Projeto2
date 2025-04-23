package br.com.example.fecapayplus.services;

import java.util.List;

import br.com.example.fecapayplus.data.dtos.BoletosDTO;
import br.com.example.fecapayplus.data.dtos.UserBalanceDTO;
import br.com.example.fecapayplus.data.model.Boleto;
import br.com.example.fecapayplus.data.model.User;
import br.com.example.fecapayplus.data.dtos.UserDTO;
import br.com.example.fecapayplus.data.dtos.ChangePasswordDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


//Serviço para lidar com a manipulação de dados entre o backend
public interface IApiService {

    //Rota para cadastro
    @POST("api/cadastro")
    Call<Void> registerUser(@Body User user);

    //Rota para acessar a aplicação
    @POST("api/entrar")
    Call<UserDTO> loginUser(@Body User user);

    //Rota para alterar senha
    @PATCH("api/alterar-senha/{ra}")
    Call<Void> changePassowrd(@Path("ra") int ra, @Body ChangePasswordDTO request);

    //Rota para deletar conta
    @DELETE("api/deletar/{ra}")
    Call<Void> deleteAccount(@Path("ra") int ra);

    //Rota para verificar saldo
    @GET("api/saldo/{ra}")
    Call<UserBalanceDTO> getSaldo(@Path("ra") Integer ra);

    //rota para verificar boletos
    @GET("api/boletos/{ra}")
    Call<List<Boleto>> getBoletos(@Path("ra") int ra);

    //Rota para pagar boletos
    @PATCH("api/boletos/pagar/{boleto_id}/{ra}")
    Call<ResponseBody> pagarBoleto(@Path("boleto_id") int boletoId, @Path("ra") int ra);


}
