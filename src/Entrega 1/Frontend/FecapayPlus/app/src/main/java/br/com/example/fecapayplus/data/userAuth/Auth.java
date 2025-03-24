package br.com.example.fecapayplus.data.userAuth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

//Classe para armazenar a autenticação do usuário
public class Auth {

    SharedPreferences sharedPreferences;
    public Auth(Context context){
        this.sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
    }

    //Método para salvar o Token fornecido pelo backend
    public void saveToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_TOKEN", token);
        Log.d("TOKEN_DEBUG", "Token salvo: " + token);
        editor.apply();
    }

    //Método para armazenar o RA do usuário
    public void saveRa(Integer ra){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_RA", ra);
        Log.d("RA_DEBUG", "R.A salvo: " + ra);
        editor.apply();
    }

    //Métodos para acessar as informações salvas
    public String getToken(){
        return sharedPreferences.getString("USER_TOKEN", null);
    }

    public Integer getRa(){
        return  sharedPreferences.getInt("USER_RA", -1);
    }


    //Método para verificar se o token do usuário ainda existe
    public boolean isUserLogged(){
        return sharedPreferences.getString("USER_TOKEN", null) != null;
    }

    //Método para remover todos os dados armazenados referentes a autenticação do usuário
    public void removeData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USER_TOKEN");
        Log.d("DATA_DEBUG", "Token: " + getToken());
        editor.remove("USER_RA");
        Log.d("DATA_DEBUG", "R.A: " + getRa());
        editor.apply();
    }
}
