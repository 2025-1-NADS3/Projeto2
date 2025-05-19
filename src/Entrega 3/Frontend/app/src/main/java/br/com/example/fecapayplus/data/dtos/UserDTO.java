package br.com.example.fecapayplus.data.dtos;

import com.google.gson.annotations.SerializedName;


//Classe DTO
public class UserDTO {

    //Mapeia o retorno do JSON do back end para uma chave "nome"
    @SerializedName("nome")
    private String nome;
    @SerializedName("sobrenome")
    private String sobrenome;
    @SerializedName("email")
    private String email;

    //Mapeia o retorno do JSON do back end para uma chave "ra"
    @SerializedName("ra")
    private Integer ra;


    //Gets para acessar as informações recebidas
    public  Integer getRa(){
        return ra;
    }
    public String getNome(){
        return nome;
    }
    public String getSobrenome(){return sobrenome;}
    public String getEmail(){return email;}

}
