package br.com.example.fecapayplus.utils;


import android.content.Context;
import android.content.Intent;

import br.com.example.fecapayplus.ui.activities.LoginActivity;
import br.com.example.fecapayplus.userauth.Auth;

public class UserActions {
    public static void logOut(Context context){

        //Instaância da classe Auth para lidar com o gerenciameto dos dados
        Auth auth = new Auth(context);
        auth.removeData();

        //Intent para iniciar a atividade para a pagina de login
        Intent intent = new Intent(context, LoginActivity.class);
        //Faz com que todas as atividades sejam removidas da pilha para que não haja possibilidade de voltar activity de outras formas
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //Função para ativar o botão e realizar o logout
        context.startActivity(intent);
    }

}
