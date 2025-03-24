package br.com.example.fecapayplus.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.User;
import br.com.example.fecapayplus.data.userAuth.Auth;
import br.com.example.fecapayplus.ui.signup.SignupActivity;
import br.com.example.fecapayplus.ui.userProfile.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    //Definindo variáveis de interface
    private EditText inputRa, inputPassword;
    private Button loginBtn;
    private TextView passwordLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //Verifica se o usuário está logado
        isLogged();

        //Instanciamento do botào de login
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setEnabled(false);

        //Instanciamento dos inputs referentes ao login
        inputRa = findViewById(R.id.raInputLogin);
        inputPassword = findViewById(R.id.passwordInputLogin);

        passwordLoginError = findViewById(R.id.errorMessageLoginPage);

        //Função TextWatchr para verificar os dados inseridos pelo usuário
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };


        //Implementação da função TextWatcher dentro do input
        inputRa.addTextChangedListener(textWatcher);
        inputPassword.addTextChangedListener(textWatcher);


        //Chamada da função validateForm para liberar o login do usuário
        loginBtn.setOnClickListener( view -> {
            if(validateForm()){
                loginUser();

            }
        });

        setupButtons();
    }


    //Método para verificar ser há dados de login armazenados
    private void isLogged(){
        Auth auth = new Auth(this);
        Intent intent = new Intent(this, ProfileActivity.class);

        if(auth.isUserLogged()){
            startActivity(intent);
            finish();
        }

    }

    //Método para realizar o login
    private void loginUser(){
        Auth auth = new Auth(this);
        Integer ra = Integer.parseInt(inputRa.getText().toString().trim());
        String senha = inputPassword.getText().toString().trim();

        User user = new User(ra, senha);

        //Instanciamento do repositório de login para lidar com a IApiService
        LoginRepository loginRepository = new LoginRepository();

        loginRepository.loginUser(user, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //Verifica as informações enviadas para o servidor
                if(response.isSuccessful() && response.body() != null && response.body().getToken() != null && response.body().getRa() != null){
                    String token = response.body().getToken();
                    Integer ra = response.body().getRa();

                    auth.saveRa(ra);
                    auth.saveToken(token);

                    Toast.makeText(LoginActivity.this, "Login realizado.", Toast.LENGTH_SHORT).show();
                    endLogin();

                    //Tratamento de erros para o usuário
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Senha incorreta.", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 400) {
                    Toast.makeText(LoginActivity.this, "Conta não encontrada", Toast.LENGTH_SHORT).show();
                    
                } else{
                    Log.e("API_ERROR", "Código: " + response.code() + " | Erro: " + response.message());
                    Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Log.e("API_ERROR", "Falha: ", throwable);
                Toast.makeText(LoginActivity.this, "Erro desconhecido", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Método para finalizar o login e direcionar o usuário para a tela de perfil
    private void endLogin(){
        Intent intent = new Intent(this, ProfileActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    //Método para direcionar o usuário da página de login à página de cadastro
    private void setupButtons(){
        Button signupPageBtn = findViewById(R.id.signupPageBtn);
        signupPageBtn.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
    }


    //Método para validar o preenchimento dos campos e alterar a cor dos botões
    private void validateFields(){

        boolean filledfields = areFieldsFilled();

        loginBtn.setEnabled(filledfields);
        loginBtn.setBackgroundTintList((
                filledfields
                        ? ColorStateList.valueOf(Color.parseColor("#48bc9c"))
                        : ColorStateList.valueOf(Color.parseColor("#9F9F9F"))
                ));
    }

    //Metodo para validar se os campos estão preenchidos
    private boolean areFieldsFilled(){
        return  !inputRa.getText().toString().isEmpty() &&
                !inputPassword.getText().toString().isEmpty();
    }

    //Metodo para validar o formulário.
    private boolean validateForm(){
        boolean valid = true;

        String ra = inputRa.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(ra.isEmpty() || password.isEmpty()){
            valid = false;
        }

        return valid;
    }
}
