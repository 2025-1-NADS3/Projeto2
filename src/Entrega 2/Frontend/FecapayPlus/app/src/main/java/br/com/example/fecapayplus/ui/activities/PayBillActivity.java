package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.repository.PayBillRepository;
import br.com.example.fecapayplus.userauth.Auth;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_bill);

        TextView txtBoletoId = findViewById(R.id.txtPagarBoletoId);
        TextView txtBoletoStatus = findViewById(R.id.txtPagarBoletoStatus);
        TextView txtBoletoData = findViewById(R.id.txtPagarBoletoData);
        TextView txtBoletoVencimento = findViewById(R.id.txtPagarBoletoVencimento);
        TextView txtBoletoValor = findViewById(R.id.txtPagarBoletoValor);
        TextView txtBoletoCodigo = findViewById(R.id.txtPagarBoletoCodigo);
        Button btnPagarBoletoSaldo = findViewById(R.id.btnPagarSaldo);
        Button btnVoltarCard = findViewById(R.id.btnVoltar);
        Button confirmarPagamento = findViewById(R.id.btnConfirmarSaldo);
        CardView cardConfirm = findViewById(R.id.cardPayBill);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            txtBoletoId.setText("ID: " + bundle.getInt("boleto_id"));
            txtBoletoStatus.setText("Status: " + bundle.getString("status"));
            txtBoletoData.setText("Data: " + bundle.getString("data_boleto"));
            txtBoletoVencimento.setText("Vencimento: " + bundle.getString("vencimento"));
            txtBoletoValor.setText(bundle.getString("valor"));
            txtBoletoCodigo.setText("Código: " + bundle.getInt("codigo"));
        }

        btnPagarBoletoSaldo.setOnClickListener(view -> {
            cardConfirm.setVisibility(View.VISIBLE);
        });

        btnVoltarCard.setOnClickListener(view -> {
            cardConfirm.setVisibility(View.GONE);
        });
        confirmarPagamento.setOnClickListener(view -> {
            payBill();
        });


    }

    //Metodo para enviar uma chamada a API e pagar o boleto
    private void payBill(){
        Auth auth = new Auth(this);
        Bundle bundle = getIntent().getExtras();
        int ra = auth.getRa();
        int boletoId = bundle.getInt("boleto_id", -1);

        Intent intent = new Intent(this, HomeActivity.class);

        PayBillRepository payBillRepository = new PayBillRepository();
        payBillRepository.payBill(boletoId, ra, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("PayBill", "Boleto pago com sucesso!");
                    Toast.makeText(PayBillActivity.this, "Pagamento realizado!", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);

                } else {
                    Log.e("PayBill", "Erro ao pagar boleto: " + response.message());
                    Toast.makeText(PayBillActivity.this, "Erro no pagamento: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("PayBill", "Falha na requisição: " + t.getMessage());
                Toast.makeText(PayBillActivity.this, "Falha do servidor", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
