package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.example.fecapayplus.R;

public class ComprovanteActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comprovante);
        setupNavigation();

        // Recebe os dados do Bundle
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        // Obtém os parâmetros obrigatórios
        String tipoPagamento = extras.getString("tipo");
        double valor = extras.getDouble("valor");

        // Valida os parâmetros obrigatórios
        if (tipoPagamento == null) {
            Toast.makeText(this, "Tipo de pagamento não informado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Formata os valores
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault());
        String dataHora = dateFormat.format(new Date());

        // Configura os textos
        TextView textTipo = findViewById(R.id.textTipo);
        TextView textValor = findViewById(R.id.textValor);
        TextView textData = findViewById(R.id.textData);

        textTipo.setText(String.format("Tipo: %s", tipoPagamento));
        textValor.setText(String.format("Valor: %s", currencyFormat.format(valor)));
        textData.setText(String.format("Data: %s", dataHora));

        // Configura o botão para voltar
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}