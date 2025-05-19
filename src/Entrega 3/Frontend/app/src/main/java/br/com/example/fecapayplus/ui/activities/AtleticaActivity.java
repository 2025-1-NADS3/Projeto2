package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.dtos.ProdutoDTO;
import br.com.example.fecapayplus.data.model.Produto;
import br.com.example.fecapayplus.data.repository.ProdutoRepository;
import br.com.example.fecapayplus.ui.adapters.ProdutoAdapter;
import br.com.example.fecapayplus.utils.CartManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtleticaActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu
        implements ProdutoAdapter.OnProdutoClickListener {
    public String loja = "atletica";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atletica);
        setupNavigation();

        TextView textSemItens = findViewById(R.id.textSemItens);
        RecyclerView recyclerView = findViewById(R.id.recyclerProduto);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        ProdutoRepository repo = new ProdutoRepository();
        repo.getProdutos(loja, new Callback<ProdutoDTO>() {
            @Override
            public void onResponse(Call<ProdutoDTO> call, Response<ProdutoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> produtos = response.body().getProdutos();
                    for (Produto produto : produtos) {
                        produto.setLoja(loja);  // Define a loja aqui
                    }
                    if (produtos.isEmpty()) {
                        textSemItens.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        textSemItens.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new ProdutoAdapter(produtos, (ProdutoAdapter.OnProdutoClickListener) AtleticaActivity.this));
                    }
                }else {
                    textSemItens.setText("Erro ao carregar os itens.");
                    textSemItens.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProdutoDTO> call, Throwable t) {
                textSemItens.setText("Erro na conexão.");
                textSemItens.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Log.e("Erro", "Erro ao carregar produtos: " + t.getMessage());
            }
        });

        ImageButton btnCarrinho = findViewById(R.id.goToCartBtn);
        btnCarrinho.setOnClickListener(v -> {
            startActivity(new Intent(this, CarrinhoActivity.class));
        });
    }

    @Override
    public void onDetalhesClick(Produto produto) {
        Intent intent = new Intent(this, DetalhesDoProdutoActivity.class);
        intent.putExtra("produto_id", produto.getId());
        intent.putExtra("produto_nome", produto.getNome());
        intent.putExtra("produto_preco", produto.getPreco());
        intent.putExtra("produto_descricao", produto.getDescricao());
        intent.putExtra("produto_imagem", produto.getImagem());
        intent.putExtra("produto_estoque", produto.getEstoque());
        intent.putExtra("produto_categoria", produto.getCategoria());
        intent.putExtra("produto_loja", loja);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Produto produto) {
        CartManager.getInstance().addItem(produto);
        produto.setLoja(loja);
        Snackbar.make(findViewById(android.R.id.content),
                        produto.getNome() + " adicionado ao carrinho",
                        Snackbar.LENGTH_SHORT)
                .setAction("Ver", v -> {
                    startActivity(new Intent(this, CarrinhoActivity.class));
                })
                .setBackgroundTint(getResources().getColor(R.color.purple))
                .setTextColor(getResources().getColor(android.R.color.white))
                .setActionTextColor(getResources().getColor(android.R.color.white))
                .show();
    }
}