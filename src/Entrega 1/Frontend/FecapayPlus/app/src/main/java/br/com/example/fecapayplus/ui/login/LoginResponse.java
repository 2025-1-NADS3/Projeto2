package br.com.example.fecapayplus.ui.login;

import com.google.gson.annotations.SerializedName;


//Classe DTO
public class LoginResponse {

    //Mapeia o retorno do JSON do back end para uma chave "token"
    @SerializedName("token")
    private String token;

    //Mapeia o retorno do JSON do back end para uma chave "ra"
    @SerializedName("ra")
    private Integer ra;

    //Gets para acessar as informações recebidas
    public  Integer getRa(){
        return ra;
    }
    public String getToken(){
        return token;
    }
}
