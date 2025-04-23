package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;

import java.util.List;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Boleto;
import br.com.example.fecapayplus.data.repository.BoletoRepository;
import br.com.example.fecapayplus.userauth.Auth;
import br.com.example.fecapayplus.utils.Formatters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoletoActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {
    private TextView txtBoletoId, txtBoletoStatus, txtBoletoData, txtBoletoVencimento, txtBoletoValor, txtBoletoCodigo;
    private Button btnPagarBoleto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_boleto);
        setupNavigation();
        getBoletos();
    }

    protected void getBoletos(){
        Auth auth = new Auth(this);
        Integer ra = auth.getRa();
        Log.d("RA", "Usuario: " + ra);

        BoletoRepository boletoRepository = new BoletoRepository();
        boletoRepository.getBoletos(ra, new Callback<List<Boleto>>() {
            @Override
            public void onResponse(Call<List<Boleto>> call, Response<List<Boleto>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Boleto> boletos = response.body();
                    Log.e("Boletos", "Total de boletos recebidos: " + boletos.size());
                    for (Boleto b : boletos) {
                        Log.e("Boletos", "ID: " + b.getId() + " - Valor: R$ " + b.getValor());
                    }

                    addBoletos(boletos);
                }
            }

            @Override
            public void onFailure(Call<List<Boleto>> call, Throwable throwable) {
                Log.e("Erro", "Erro interno: " + throwable.getMessage());
            }
        });
    }


    //Metodo para receber e criar os boletos dinamicamente no ScrollView
    private void addBoletos(List<Boleto> listaDeBoletos){
        LinearLayout boletosContainer = findViewById(R.id.boletoContainer);
        boletosContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        //Loop para criar boleto por boleto
        for(Boleto boleto : listaDeBoletos){
            View boletoView = inflater.inflate(R.layout.boleto_card, boletosContainer, false);

            TextView txtBoletoId = boletoView.findViewById(R.id.txtBoletoId);
            TextView txtBoletoStatus = boletoView.findViewById(R.id.txtBoletoStatus);
            TextView txtBoletoData = boletoView.findViewById(R.id.txtBoletoData);
            TextView txtBoletoVencimento = boletoView.findViewById(R.id.txtBoletoVencimento);
            TextView txtBoletoValor = boletoView.findViewById(R.id.txtBoletoValor);
            TextView txtBoletoCodigo = boletoView.findViewById(R.id.txtBoletoCodigo);
            Button btnPagar = boletoView.findViewById(R.id.btnSelectBoleto);

            txtBoletoId.setText("Boleto ID: " + boleto.getId());
            txtBoletoStatus.setText("Status: " + boleto.getStatus());
            txtBoletoData.setText("Data referência: " + Formatters.formatedDateMonth(boleto.getData_boleto()));
            txtBoletoVencimento.setText("Vencimento: " + Formatters.formatedDate(boleto.getData_vencimento()));
            txtBoletoValor.setText(Formatters.formatedBalance(boleto.getValor()));
            txtBoletoCodigo.setText("Código: " + boleto.getCodigo());

            btnPagar.setOnClickListener(v -> {
                Intent intent = new Intent(this, PayBillActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("boleto_id", boleto.getId());
                bundle.putString("status", boleto.getStatus());
                bundle.putString("data_boleto", Formatters.formatedDateMonth(boleto.getData_boleto()));
                bundle.putString("vencimento", Formatters.formatedDate(boleto.getData_vencimento()));
                bundle.putString("valor", Formatters.formatedBalance(boleto.getValor()));
                bundle.putInt("codigo", boleto.getCodigo());

                intent.putExtras(bundle);
                startActivity(intent);
            });


            boletosContainer.addView(boletoView);
        }
    }

}
