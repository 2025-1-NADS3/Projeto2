package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.example.fecapayplus.R;

import br.com.example.fecapayplus.data.model.User;
import br.com.example.fecapayplus.data.repository.SignupRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SignupActivity extends AppCompatActivity {

    //Definindo variáveis de inteface
    private EditText inputName, inputSurname, inputRa, inputEmail, inputPassword;
    private Button signupBtn;
    private TextView passwordLengthError;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        //Instanciamento dos inputs de cadastro
        inputName = findViewById(R.id.nameInputSignup);
        inputSurname = findViewById(R.id.surnameInputSignup);
        inputRa = findViewById(R.id.raInputSignup);
        inputEmail = findViewById(R.id.emailInputSignup);
        inputPassword = findViewById(R.id.passwordInputSignup);

        passwordLengthError = findViewById(R.id.passwordLengthError);

        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setEnabled(false);

        //Text Watcher para verificar a digitção do usuário
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
        inputName.addTextChangedListener(textWatcher);
        inputSurname.addTextChangedListener(textWatcher);
        inputRa.addTextChangedListener(textWatcher);
        inputEmail.addTextChangedListener(textWatcher);
        inputPassword.addTextChangedListener(textWatcher);


        //Chamada da função validateForm para liberar o cadastro e enviar as informações.
        signupBtn.setOnClickListener(view -> {
            if(validateForm()){
                registerUser();
                endSignup();
            }
        });
    }


    //Método para finalizar a página de cadastro e direcionar o usuário à pagina de login
    private void endSignup(){
        Intent intent = new Intent(this, LoginActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    //Método para registrar o usuário
    private void registerUser() {

        //Acessando os dados que serão enviados para o backend
        String nome = inputName.getText().toString().trim();
        String sobrenome = inputSurname.getText().toString().trim();
        Integer ra = Integer.parseInt(inputRa.getText().toString().trim());
        String email = inputEmail.getText().toString().trim();
        String senha = inputPassword.getText().toString().trim();

        //Instancia do construtor User
        User user = new User(nome, sobrenome, ra, email, senha);
        //Instanciamento do repositório de cadastro para lidar com a IApiService
        SignupRepository signupRepository = new SignupRepository();

        //Uso do método para lidar com a IApiService
        signupRepository.registerUser(user, new Callback<Void>() {
            @Override
            //Envia os dados para o backend e aguarda o retorno
            public void onResponse(Call<Void> call, Response<Void> response) {
                //Verifica as informações enviadas para o servidor
                if(response.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                    //Tratamento de erros para o usuário
                } else if (response.code() == 400){
                    Toast.makeText(SignupActivity.this, "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("API_ERROR", "Código: " + response.code() + " | Erro: " + response.message());
                    Toast.makeText(SignupActivity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("API_ERROR", "Falha: ", throwable);
                Toast.makeText(SignupActivity.this, "Erro desconhecido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método para validar o preenchimento dos campos e alterar a cor dos botões
    private void validateFields(){

        boolean fieldsFill = fieldsFilled() ;

        signupBtn.setEnabled(fieldsFill);
        signupBtn.setBackgroundTintList((
                fieldsFill
                        ? ColorStateList.valueOf(Color.parseColor("#b06ccc"))
                        : ColorStateList.valueOf(Color.parseColor("#9F9F9F"))
        ));

    }

    //Metodo para validar se os campos estão preenchidos
    private boolean fieldsFilled() {
        return !inputName.getText().toString().trim().isEmpty() &&
                !inputSurname.getText().toString().trim().isEmpty() &&
                !inputRa.getText().toString().trim().isEmpty() &&
                !inputEmail.getText().toString().trim().isEmpty() &&
                !inputPassword.getText().toString().trim().isEmpty();
    }

    //Metodo para validar o formulário.
    private boolean validateForm(){
        String name = inputName.getText().toString().trim();
        String surname = inputSurname.getText().toString().trim();
        String ra = inputRa.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        boolean valid = true;

        if(
                name.isEmpty()
                ||surname.isEmpty()
                ||ra.isEmpty()
                ||email.isEmpty()
                ||password.isEmpty()
        ){
            valid = false;
        }

        //Verifica se o email tem o padrão definido
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(SignupActivity.this, "Formato de E-mail inválido", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        //Verifica se o tamanho da senha é menor do que 8 caracteres
        if(inputPassword.getText().toString().length() < 8){
            passwordLengthError.setVisibility(View.VISIBLE);
            valid = false;

        } else{
            passwordLengthError.setVisibility(View.GONE);
        }

        return valid;

    }



}
