package br.com.example.fecapayplus.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.dtos.UserBalanceDTO;
import br.com.example.fecapayplus.data.repository.HomeRepository;
import br.com.example.fecapayplus.userauth.Auth;
import br.com.example.fecapayplus.utils.Formatters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {
    private TextView saldoView;
    private Button addCreditosBtn, boletoBtn, papelariaBtn, livrariaBtn, cantinaBtn, atleticaBtn, comprasBtn, financasBtn;
    private CardView cardTransacoes;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        saldoView = findViewById(R.id.TxtSaldo);
        setupNavigation();
        getBalance();

        boletoBtn = findViewById(R.id.homeBtnBoleto);
        addCreditosBtn = findViewById(R.id.btnHomeCreditos);
        papelariaBtn = findViewById(R.id.homeBtnPapelaria);
        livrariaBtn = findViewById(R.id.homeBtnLivraria);
        cantinaBtn = findViewById(R.id.homeBtnCantina);
        atleticaBtn = findViewById(R.id.homeBtnAtletica);
        cardTransacoes = findViewById(R.id.cardSaldo);
        comprasBtn = findViewById(R.id.homeBtnMinhasCompras);
        financasBtn = findViewById(R.id.homeBtnTransferencias);


        setupButtons(boletoBtn, this, BoletoActivity.class);
        setupButtons(addCreditosBtn, this, AdicionarCreditosActivity.class);
        setupButtons(cantinaBtn, this, CantinaActivity.class);
        setupButtons(papelariaBtn, this, PapelariaActivity.class);
        setupButtons(atleticaBtn, this, AtleticaActivity.class);
        setupButtons(livrariaBtn, this, LivrariaActivity.class);
        setupButtons(comprasBtn, this, ComprasActivity.class);
        setupButtons(financasBtn, this, FinancasActivity.class);

        cardTransacoes.setOnClickListener(view -> {
            Intent intent = new Intent(this, TransacoesActivity.class);
            startActivity(intent);
        });

    }

    //Metodo para receber o saldo do usuário
    protected void getBalance(){
        Auth auth = new Auth(this);
        int ra = auth.getRa();
        Log.d("RA", "Usuario: " + ra);

        HomeRepository homeRepository = new HomeRepository();
        homeRepository.getSaldo(ra, new Callback<UserBalanceDTO>() {
            @Override
            public void onResponse(Call<UserBalanceDTO> call, Response<UserBalanceDTO> response) {
                if(response.isSuccessful() && response.body() != null){
                    double saldo = response.body().getSaldo();
                    saldoView.setText(Formatters.formatedBalance(saldo));
                }else {
                    Log.e("Erro ","Falha ao obter saldo." + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserBalanceDTO> call, Throwable throwable) {
                Log.e("Erro", "Erro interno: " + throwable.getMessage());
            }
        });
    }

    //Setup para levar cada botão a activity referente
    public void setupButtons(Button button, Context context, Class<?> activityClass){
        Intent intent = new Intent(context, activityClass);
        button.setOnClickListener(view -> {
            context.startActivity(intent);
        });
    }



}
