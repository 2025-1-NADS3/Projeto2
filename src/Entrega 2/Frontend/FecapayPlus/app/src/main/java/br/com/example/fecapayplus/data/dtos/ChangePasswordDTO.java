package br.com.example.fecapayplus.data.dtos;

import com.google.gson.annotations.SerializedName;

//Classe para lidar com as mudanças de senha
public class ChangePasswordDTO {


    //Anotações para mapear o campo do JSON
    @SerializedName("senhaAtual")
    private String senhaAtual;
    @SerializedName("novaSenha")
    private String novaSenha;

    //Contrutor para passar os valores atuais da senha.
    public ChangePasswordDTO(String senhaAtual, String novaSenha){
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }


}
