package br.com.example.fecapayplus.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.TransferenciaRequest;
import br.com.example.fecapayplus.data.model.TransferenciaResponse;
import br.com.example.fecapayplus.data.repository.FinancasRepository;
import br.com.example.fecapayplus.userauth.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinancasActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    private EditText editRaDestino, editValor;
    private Button btnTransferir;

    private Auth auth;
    private FinancasRepository financasRepository;
    private Handler handler = new Handler();


    private int raDestino;
    private double valor;
    private int raOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financas);
        EdgeToEdge.enable(this);

        setupNavigation();
        inicializarDependencias();
        inicializarViews();
    }

    private void inicializarDependencias() {
        auth = new Auth(this);
        financasRepository = new FinancasRepository();
    }

    private void inicializarViews() {
        editRaDestino = findViewById(R.id.editRaDestino);
        editValor = findViewById(R.id.editValor);
        btnTransferir = findViewById(R.id.btnTransferir);

        btnTransferir.setOnClickListener(v -> validarDadosTransferencia());
    }

    private void validarDadosTransferencia() {
        String raDestinoStr = editRaDestino.getText().toString();
        String valorStr = editValor.getText().toString();

        if (raDestinoStr.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            raDestino = Integer.parseInt(raDestinoStr);
            valor = Double.parseDouble(valorStr);
            raOrigem = auth.getRa();

            mostrarDialogConfirmacao();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_confirmacao_transferencia, null);
        builder.setView(view);

        TextView textMensagem = view.findViewById(R.id.textMensagemConfirmacao);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmar);

        String mensagem = String.format("Transferir R$ %.2f para o RA %d?", valor, raDestino);
        textMensagem.setText(mensagem);

        Dialog dialog = builder.create();
        dialog.show();

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnConfirmar.setOnClickListener(v -> {
            dialog.dismiss();
            realizarTransferencia();
        });
    }

    private void realizarTransferencia() {
        TransferenciaRequest request = new TransferenciaRequest(raOrigem, raDestino, valor);
        Toast.makeText(this, "Processando transferência...", Toast.LENGTH_SHORT).show();

        financasRepository.transferirSaldo(request, new Callback<TransferenciaResponse>() {
            @Override
            public void onResponse(Call<TransferenciaResponse> call, Response<TransferenciaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(FinancasActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    editRaDestino.setText("");
                    editValor.setText("");


                    handler.postDelayed(() -> {
                        Intent intent = new Intent(FinancasActivity.this, ComprovanteActivity.class);
                        intent.putExtra("valor", valor);
                        intent.putExtra("tipo", "transferencia");
                        startActivity(intent);
                    }, 2000);

                } else {
                    Toast.makeText(FinancasActivity.this, "Erro na transferência", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TransferenciaResponse> call, Throwable t) {
                Toast.makeText(FinancasActivity.this, "Falha na conexão", Toast.LENGTH_LONG).show();
            }
        });
    }
}