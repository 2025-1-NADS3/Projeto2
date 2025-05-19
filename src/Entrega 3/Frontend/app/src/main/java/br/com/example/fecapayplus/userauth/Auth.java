package br.com.example.fecapayplus.userauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import br.com.example.fecapayplus.data.model.User;


//Classe para armazenar a autenticação do usuário
public class Auth {

    SharedPreferences sharedPreferences;
    public Auth(Context context){
        this.sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
    }


    //Método para armazenar o RA do usuário
    public void saveRa(Integer ra){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_RA", ra);
        Log.d("RA_DEBUG", "R.A salvo: " + ra);
        editor.apply();
    }

    public void saveNome(String nome){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_NOME", nome);
        Log.d("NOME_DEBUG", "NOME Salvo: " + nome);
        editor.apply();
    }

    public void saveSobrenome(String sobrenome){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_SOBRENOME", sobrenome);
        Log.d("NOME_DEBUG", "Sobrenome Salvo: " + sobrenome);
        editor.apply();
    }

    public void saveEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_EMAIL", email);
        Log.d("NOME_DEBUG", "Email Salvo: " + email);
        editor.apply();
    }

    //Métodos para acessar as informações salvas

    public String getNome(){
        return sharedPreferences.getString("USER_NOME", null);
    }
    public String getSobrenome(){
        return sharedPreferences.getString("USER_SOBRENOME", null);
    }
    public String getEmail(){
        return sharedPreferences.getString("USER_EMAIL", null);
    }

    public Integer getRa(){
        return  sharedPreferences.getInt("USER_RA", -1);
    }


    //Método para verificar se o token do usuário ainda existe
    public boolean isUserLogged(){
        return sharedPreferences.getInt("USER_RA", -1) != -1;
    }

    //Método para remover todos os dados armazenados referentes a autenticação do usuário
    public void removeData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("USER_NOME");
        editor.remove("USER_SOBRENOME");
        editor.remove("USER_EMAIL");
        editor.remove("USER_RA");
        editor.apply();
    }


}
