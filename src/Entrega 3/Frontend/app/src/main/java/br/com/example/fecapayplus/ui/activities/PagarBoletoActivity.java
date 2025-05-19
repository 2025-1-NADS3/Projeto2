package br.com.example.fecapayplus.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.repository.PayBillRepository;
import br.com.example.fecapayplus.userauth.Auth;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagarBoletoActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagar_boleto);
        setupNavigation();

        TextView txtBoletoData = findViewById(R.id.txtPagarBoletoData);
        TextView txtBoletoVencimento = findViewById(R.id.txtPagarBoletoVencimento);
        TextView txtBoletoValor = findViewById(R.id.txtPagarBoletoValor);
        TextView txtBoletoCodigo = findViewById(R.id.txtPagarBoletoCodigo);
        Button btnPagarBoletoSaldo = findViewById(R.id.btnPagarSaldo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            txtBoletoData.setText("Data: " + bundle.getString("data_boleto"));
            txtBoletoVencimento.setText("Vencimento: " + bundle.getString("vencimento"));
            txtBoletoValor.setText(bundle.getString("valor"));
            txtBoletoCodigo.setText("Código: " + bundle.getInt("codigo"));
        }

        btnPagarBoletoSaldo.setOnClickListener(view -> {
            mostrarDialogoConfirmacao(bundle.getString("valor"), bundle.getInt("boleto_id"));
        });
    }

    private void mostrarDialogoConfirmacao(String valorBoleto, int boletoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_payment, null);
        builder.setView(dialogView);

        TextView dialogValor = dialogView.findViewById(R.id.dialogValor);
        Button btnConfirmar = dialogView.findViewById(R.id.dialogBtnConfirm);
        Button btnCancelar = dialogView.findViewById(R.id.dialogBtnCancel);

        dialogValor.setText("Valor: " + valorBoleto);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirmar.setOnClickListener(v -> {
            dialog.dismiss();
            pagarBoleto(boletoId);
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void pagarBoleto(int boletoId) {
        Auth auth = new Auth(this);
        int ra = auth.getRa();

        PayBillRepository payBillRepository = new PayBillRepository();
        payBillRepository.payBill(boletoId, ra, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("PayBill", "Boleto pago com sucesso!");
                    Toast.makeText(PagarBoletoActivity.this, "Pagamento realizado!", Toast.LENGTH_SHORT).show();

                    // Redirecionar para o comprovante
                    Bundle bundle = getIntent().getExtras();
                    Intent intent = new Intent(PagarBoletoActivity.this, ComprovanteActivity.class);
                    intent.putExtra("tipo", "Boleto");
                    intent.putExtra("valor", "valor");

                    new Handler().postDelayed(() -> {
                        startActivity(intent);
                        finish();
                    }, 1500);
                } else {
                    Log.e("PayBill", "Erro ao pagar boleto: " + response.message());
                    Toast.makeText(PagarBoletoActivity.this, "Erro no pagamento: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("PayBill", "Falha na requisição: " + t.getMessage());
                Toast.makeText(PagarBoletoActivity.this, "Falha do servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}