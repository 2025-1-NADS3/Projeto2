package br.com.example.fecapayplus.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.dtos.CompraRequestDTO;
import br.com.example.fecapayplus.data.dtos.ProdutoCompraDTO;
import br.com.example.fecapayplus.data.model.ItemCarrinho;
import br.com.example.fecapayplus.data.repository.PayRepository;
import br.com.example.fecapayplus.userauth.Auth;
import br.com.example.fecapayplus.utils.CartManager;
import br.com.example.fecapayplus.ui.adapters.CarrinhoAdapter;
import br.com.example.fecapayplus.utils.DinheiroUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarrinhoActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu implements CarrinhoAdapter.OnItemCarrinhoListener, CartManager.CartListener {

    private RecyclerView recyclerView;
    private CarrinhoAdapter adapter;
    private TextView txtTotal;
    private Button btnFinalizar;
    private ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        EdgeToEdge.enable(this);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setupNavigation();

        recyclerView = findViewById(R.id.recycler_carrinho);
        txtTotal = findViewById(R.id.txt_total);
        btnFinalizar = findViewById(R.id.btn_finalizar);
        btnHome = findViewById(R.id.goToHomeBtn);

        adapter = new CarrinhoAdapter(CartManager.getInstance().getItens(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotal();

        btnFinalizar.setOnClickListener(view -> {
            finalizarCompra();
        });
        btnHome.setOnClickListener(view -> {
            retornarParaHome();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CartManager.getInstance().addListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        CartManager.getInstance().addListener(null);
    }

    private void updateTotal() {
        txtTotal.setText(DinheiroUtils.format(CartManager.getInstance().getTotal()));
    }

    private void retornarParaHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onQuantidadeChanged(ItemCarrinho item, int novaQuantidade) {
        CartManager.getInstance().updateItemQuantity(item.getProdutoId(), novaQuantidade);
    }

    @Override
    public void onRemoverItem(ItemCarrinho item) {
        CartManager.getInstance().removeItem(item.getProdutoId());
    }

    @Override
    public void onCartUpdated(List<ItemCarrinho> itens) {
        adapter = new CarrinhoAdapter(itens, this);
        recyclerView.setAdapter(adapter);
        updateTotal();
    }

    public void finalizarCompra(){
        Auth auth = new Auth(this);
        int ra = auth.getRa();
        List<ItemCarrinho> itens = CartManager.getInstance().getItens();

        if (itens.isEmpty()) {
            Toast.makeText(this, "Carrinho está vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar o dialogode confirmação
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_compra, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();


        TextView txtValorTotal = dialogView.findViewById(R.id.dialog_valor_total);
        txtValorTotal.setText("Valor total: " + DinheiroUtils.format(CartManager.getInstance().getTotal()));

        Button btnConfirmar = dialogView.findViewById(R.id.btn_confirmar_compra);
        Button btnCancelar = dialogView.findViewById(R.id.btn_cancelar_compra);

        btnConfirmar.setOnClickListener(v -> {
            dialog.dismiss();
            processarPagamento(ra);
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void processarPagamento(int ra) {
        List<ItemCarrinho> itens = CartManager.getInstance().getItens();
        List<ProdutoCompraDTO> dtoList = new ArrayList<>();

        for (ItemCarrinho item : itens) {
            ProdutoCompraDTO dto = new ProdutoCompraDTO(
                    item.getProdutoId(),
                    item.getQuantidade(),
                    item.getLoja()
            );
            dtoList.add(dto);
        }

        PayRepository payRepo = new PayRepository();
        CompraRequestDTO requestDTO = new CompraRequestDTO(dtoList);
        Call<ResponseBody> call = payRepo.realizarPagamento(ra, requestDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Pagamento confirmado - ir para comprovante
                    double valorTotal = CartManager.getInstance().getTotal();
                    CartManager.getInstance().clearCart();

                    Intent intent = new Intent(CarrinhoActivity.this, ComprovanteActivity.class);
                    intent.putExtra("tipo", "Compra");
                    intent.putExtra("valor", valorTotal);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CarrinhoActivity.this, "Erro ao finalizar compra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CarrinhoActivity.this, "Erro na requisição", Toast.LENGTH_SHORT).show();
            }
        });
    }
}