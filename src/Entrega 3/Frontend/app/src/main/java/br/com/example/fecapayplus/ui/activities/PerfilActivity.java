package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.repository.ProfileRepository;
import br.com.example.fecapayplus.userauth.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    //Definindo variáveis de inteface
    private Button deleteAccoutModalBtn, changePasswordModalBtn, deteleAccBackBtn, deleteAccountBtn, backBtnChangePassword, changePasswordBtn;

    private EditText currentPasswordInput, newPasswordInput, confirmPasswordInput;
    private CardView modalChangePassword, modalDeleteAccount;

    private TextView greetingUser, errorPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        setupNavigation();



        //Instanciamento das variáveis de interface
        greetingUser = findViewById(R.id.userProfileName);

        setGreetingUser(greetingUser);


        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        deleteAccountBtn = findViewById(R.id.deleteAccountBtn);

        currentPasswordInput = findViewById(R.id.currentPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmNewPasswordInput);
        errorPassword = findViewById(R.id.errorPassword);


        deleteAccoutModalBtn = findViewById(R.id.deleteAccountCardBtn);
        deteleAccBackBtn = findViewById(R.id.deteleAccBackBtn);
        modalDeleteAccount = findViewById(R.id.cardViewDeleteAcc);

        changePasswordModalBtn = findViewById(R.id.changePasswordCardBtn);
        backBtnChangePassword = findViewById(R.id.changePasswordBackBtn);
        modalChangePassword = findViewById(R.id.cardViewChangePassword);

        //Utilizando o método para lidar com o modal.
        setupModal(deleteAccoutModalBtn, deteleAccBackBtn,modalDeleteAccount);
        setupModal(changePasswordModalBtn, backBtnChangePassword, modalChangePassword);



        //Text Watcher para verificar os campos de alterar senha.
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
        currentPasswordInput.addTextChangedListener(textWatcher);
        newPasswordInput.addTextChangedListener(textWatcher);
        confirmPasswordInput.addTextChangedListener(textWatcher);


        //Envento que valida os campos preenchidos e da seguimento na alteração da senha do usuário
        changePasswordBtn.setOnClickListener(view -> {
            if(validateForm()){
                changePassword();
            }
        });


        //Botão para deletar a conta do usuário
        deleteAccountBtn.setOnClickListener(view -> {
            deleteAccount();
        });
    }

    private void setGreetingUser(TextView textView){
        Auth auth = new Auth(this);
        String nome  = auth.getNome();
        greetingUser.setText("Seja bem vindo, " + nome);
    }
    //Método para alterar a senha
    private void changePassword(){

        //Verifica o usuário logado e obtem os dados a serem enviados para o backend
        Auth auth = new Auth(this);
        Integer ra = auth.getRa();
        String senhaAtual = currentPasswordInput.getText().toString().trim();
        String novaSenha = newPasswordInput.getText().toString().trim();


        //Instancia do Profile response para lidar com API service
        ProfileRepository profileRepository = new ProfileRepository();

        //Uso do metódo para enviar os informações para o Backend
        profileRepository.changePassword(ra, senhaAtual, novaSenha, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerfilActivity.this, "Senha alterada com sucesso.", Toast.LENGTH_SHORT).show();
                    endActivity();
                } else if (response.code() == 401) {
                    Toast.makeText(PerfilActivity.this, "Senha incorreta.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PerfilActivity.this, "Erro ao alterar senha.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(PerfilActivity.this, "Falha no servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Função para deletar o usuário deletar a conta
    private void deleteAccount(){
        Auth auth = new Auth(this);

        //Verifica o ra do usuário armazenado
        Integer ra = auth.getRa();

        //Instancia do repositório para lidar com API service
        ProfileRepository profileRepository = new ProfileRepository();

        //Uso do método para comunicar com API
        profileRepository.deleteAccount(ra, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerfilActivity.this, "Conta deletada.", Toast.LENGTH_SHORT).show();
                    endActivity();
                }
                else {
                    Toast.makeText(PerfilActivity.this, "Erro ao deletar a conta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(PerfilActivity.this, "Erro do servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Função para terminar uma activity, apagar os dados do usuário e direcioná-lo a pagina de login
    private void endActivity(){
        Auth auth = new Auth(this);

        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth.removeData();
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    //Método para validar o preenchimento dos campos e alterar a cor dos botões
    private void validateFields(){

        boolean filledfields = areFieldsFilled();

        changePasswordBtn.setEnabled(filledfields);
        changePasswordBtn.setBackgroundTintList((
                filledfields
                        ? ColorStateList.valueOf(Color.parseColor("#48bc9c"))
                        : ColorStateList.valueOf(Color.parseColor("#9F9F9F"))
        ));
    }

    //Metodo para validar se os campos estão preenchidos
    private boolean areFieldsFilled(){
        return  !currentPasswordInput.getText().toString().isEmpty() &&
                !newPasswordInput.getText().toString().isEmpty() &&
                !confirmPasswordInput.getText().toString().isEmpty();
    }

    //Metodo para validar o formulário.
    private boolean validateForm(){
        String currentPassword = currentPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();


        boolean valid = true;

        if(
                        currentPassword.isEmpty()
                        ||newPassword.isEmpty()
                        ||confirmPassword.isEmpty()
        ){
            valid = false;
            //Verifica se as senhas coincidem e retorna o tipo de erro
        }else if(!newPassword.equals(confirmPassword)){
            errorPassword.setVisibility(View.VISIBLE);
            errorPassword.setText("Senhas não coincidem.");
            valid = false;

            //Padroniza o tamanho da senha e retorna o tipo de erro
        }else if(newPassword.length() < 8){
            errorPassword.setVisibility(View.VISIBLE);
            errorPassword.setText("Mínimo 8 caracteres.");
            valid = false;

        } else {
            errorPassword.setVisibility(View.GONE);
        }


        return valid;

    }

    //Função para lidar com a abertuda e fechamento do modal
    private void setupModal(Button button, Button back, CardView cardView){
        button.setOnClickListener(view -> {
            cardView.setVisibility(View.VISIBLE);
        });
        back.setOnClickListener(view -> {
            cardView.setVisibility(View.GONE);
        });
    }


}
