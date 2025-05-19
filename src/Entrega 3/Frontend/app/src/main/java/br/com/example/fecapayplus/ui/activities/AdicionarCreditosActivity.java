package br.com.example.fecapayplus.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputEditText;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.dtos.UserBalanceDTO;
import br.com.example.fecapayplus.data.repository.BalanceRepository;
import br.com.example.fecapayplus.data.repository.HomeRepository;
import br.com.example.fecapayplus.userauth.Auth;
import br.com.example.fecapayplus.utils.Formatters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarCreditosActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {
    private double saldoAtual = 0d;
    private TextView saldoView, novoSaldo;
    private TextInputEditText addSaldo, expiryInput;
    private NestedScrollView creditCardSection;
    private LinearLayout pixSection;
    private ImageView qrCodeImage;
    private Button btnGerarPix, btnConfirmarPix, btnPagamentoSaldo;
    private boolean pixGerado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_creditos);
        setupNavigation();

        // Inicialização das views
        initViews();
        setupListeners();
        getBalance();
    }

    private void initViews() {
        pixSection = findViewById(R.id.pixSection);
        qrCodeImage = findViewById(R.id.qrCodeImage);
        btnGerarPix = findViewById(R.id.btnGerarPix);
        btnConfirmarPix = findViewById(R.id.btnConfirmarPix);
        btnPagamentoSaldo = findViewById(R.id.pagamentoSaldo);
        saldoView = findViewById(R.id.saldoTxt);
        novoSaldo = findViewById(R.id.novoSaldo);
        addSaldo = findViewById(R.id.addSaldoInput);
        creditCardSection = findViewById(R.id.creditCardSection);
        expiryInput = findViewById(R.id.expiryInput);
    }

    private void setupListeners() {
        SwitchCompat switchPagamento = findViewById(R.id.switchPagamento);
        TextView metodoPagamentoTxt = findViewById(R.id.textPagamento);

        // Listener para mudança de método de pagamento
        switchPagamento.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Modo Pix selecionado
                creditCardSection.setVisibility(View.GONE);
                pixSection.setVisibility(View.VISIBLE);
                metodoPagamentoTxt.setText("Pagamento via Pix");
                esconderTeclado();
            } else {
                // Modo Cartão selecionado
                creditCardSection.setVisibility(View.VISIBLE);
                pixSection.setVisibility(View.GONE);
                metodoPagamentoTxt.setText("Pagamento via Cartão");
            }
        });

        // Listener para campo de valor
        addSaldo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarNovoSaldo();
                verificarCamposObrigatoriosCartao();
            }
        });

        // Listener para campo de validade do cartão
        expiryInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().replaceAll("[^\\d]", "");
                if (input.length() > 2) {
                    input = input.substring(0, 2) + "/" + input.substring(2);
                }
                expiryInput.removeTextChangedListener(this);
                expiryInput.setText(input);
                expiryInput.setSelection(input.length());
                expiryInput.addTextChangedListener(this);
                verificarCamposObrigatoriosCartao();

            }
        });

        // Listeners para os botões
        btnPagamentoSaldo.setOnClickListener(view -> mostrarDialogoConfirmacaoCartao());
        btnGerarPix.setOnClickListener(v -> mostrarDialogoGerarPix());
        btnConfirmarPix.setOnClickListener(v -> mostrarDialogoConfirmarPix());
    }


    private void mostrarDialogoGerarPix() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_gerar_pix, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnConfirmar = dialogView.findViewById(R.id.btnDialogConfirmar);
        Button btnCancelar = dialogView.findViewById(R.id.btnDialogCancelar);

        btnConfirmar.setOnClickListener(v -> {
            gerarQrCodePix();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void mostrarDialogoConfirmarPix() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_pix, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnSim = dialogView.findViewById(R.id.btnDialogSim);
        Button btnNao = dialogView.findViewById(R.id.btnDialogNao);

        btnSim.setOnClickListener(v -> {
            verificarPagamentoPix();
            dialog.dismiss();
        });

        btnNao.setOnClickListener(v -> dialog.dismiss());
    }

    private void mostrarDialogoConfirmacaoCartao() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_cartao, null);
        TextView txtValor = dialogView.findViewById(R.id.dialogValorCartao);

        String valorStr = addSaldo.getText().toString().trim();
        txtValor.setText("Valor: R$ " + valorStr);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnConfirmar = dialogView.findViewById(R.id.btnDialogConfirmarCartao);
        Button btnCancelar = dialogView.findViewById(R.id.btnDialogCancelarCartao);

        btnConfirmar.setOnClickListener(v -> {
            addBalance();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    // ========== LÓGICA DE NEGÓCIO ========== //

    private void gerarQrCodePix() {
        String valorStr = addSaldo.getText().toString().trim();
        if (valorStr.isEmpty()) {
            Toast.makeText(this, "Digite um valor válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulação de geração de QR Code
        qrCodeImage.setImageResource(R.drawable.ic_qr_code_sample);
        pixGerado = true;
        btnConfirmarPix.setEnabled(true);
        Toast.makeText(this, "QR Code gerado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void verificarPagamentoPix() {
        String valorStr = addSaldo.getText().toString().trim();
        double valor = Double.parseDouble(valorStr);
        adicionarSaldoAposPix(valor);
    }

    private void adicionarSaldoAposPix(double valor) {
        Auth auth = new Auth(this);
        int ra = auth.getRa();

        BalanceRepository balanceRepository = new BalanceRepository();
        UserBalanceDTO userBalance = new UserBalanceDTO();
        userBalance.setSaldo(valor);

        showLoadingDialog();

        Call<UserBalanceDTO> call = balanceRepository.addBalance(ra, userBalance);
        call.enqueue(new Callback<UserBalanceDTO>() {
            @Override
            public void onResponse(Call<UserBalanceDTO> call, Response<UserBalanceDTO> response) {
                dismissLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    redirecionarParaComprovante("Pix", valor);
                } else {
                    Toast.makeText(AdicionarCreditosActivity.this, "Erro ao confirmar pagamento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserBalanceDTO> call, Throwable t) {
                dismissLoadingDialog();
                Toast.makeText(AdicionarCreditosActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void addBalance() {
        String valorStr = addSaldo.getText().toString().trim();
        if (valorStr.isEmpty()) {
            addSaldo.setError("Digite um valor válido!");
            return;
        }

        double valor = Double.parseDouble(valorStr);
        showLoadingDialog();

        Auth auth = new Auth(this);
        int ra = auth.getRa();

        BalanceRepository balanceRepository = new BalanceRepository();
        UserBalanceDTO userBalance = new UserBalanceDTO();
        userBalance.setSaldo(valor);

        Call<UserBalanceDTO> call = balanceRepository.addBalance(ra, userBalance);
        call.enqueue(new Callback<UserBalanceDTO>() {
            @Override
            public void onResponse(Call<UserBalanceDTO> call, Response<UserBalanceDTO> response) {
                dismissLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    redirecionarParaComprovante("Cartão", valor);
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao adicionar saldo.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserBalanceDTO> call, Throwable t) {
                dismissLoadingDialog();
                Toast.makeText(getApplicationContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirecionarParaComprovante(String tipoPagamento, double valor) {
        Intent intent = new Intent(this, ComprovanteActivity.class);
        intent.putExtra("tipo", tipoPagamento);
        intent.putExtra("valor", valor);
        startActivity(intent);
        finish();
    }

    // ========== MÉTODOS AUXILIARES ========== //

    private void showLoadingDialog() {
        // Implemente seu diálogo de loading aqui
    }

    private void dismissLoadingDialog() {
        // Implemente o fechamento do diálogo de loading aqui
    }

    private void esconderTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void getBalance() {
        Auth auth = new Auth(this);
        int ra = auth.getRa();

        HomeRepository homeRepository = new HomeRepository();
        homeRepository.getSaldo(ra, new Callback<UserBalanceDTO>() {
            @Override
            public void onResponse(Call<UserBalanceDTO> call, Response<UserBalanceDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saldoAtual = response.body().getSaldo();
                    double saldo = response.body().getSaldo();
                    saldoView.setText("Saldo atual: " + Formatters.formatedBalance(saldo));
                    atualizarNovoSaldo();
                }
            }

            @Override
            public void onFailure(Call<UserBalanceDTO> call, Throwable throwable) {
                Log.e("Erro", "Erro ao obter saldo: " + throwable.getMessage());
            }
        });
    }

    private void atualizarNovoSaldo() {
        String valorDigitado = addSaldo.getText().toString();
        double valorAdicionado = valorDigitado.isEmpty() ? 0 : Double.parseDouble(valorDigitado);
        double novoValor = saldoAtual + valorAdicionado;
        novoSaldo.setText("Novo saldo: " + Formatters.formatedBalance(novoValor));
    }
    private void verificarCamposObrigatoriosCartao() {
        String valor = addSaldo.getText().toString().trim();
        String validade = expiryInput.getText().toString().trim();

        boolean camposPreenchidos = !valor.isEmpty() && !validade.isEmpty() && validade.matches("\\d{2}/\\d{2}");

        btnPagamentoSaldo.setVisibility(camposPreenchidos ? View.VISIBLE : View.GONE);
    }

}