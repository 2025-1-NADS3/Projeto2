package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Produto;
import br.com.example.fecapayplus.utils.CartManager;

public class DetalhesDoProdutoActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_produto);
        setupNavigation();

        // Obter os dados do produto
        int produtoId = getIntent().getIntExtra("produto_id", 0);
        String nome = getIntent().getStringExtra("produto_nome");
        String preco = getIntent().getStringExtra("produto_preco");
        String descricao = getIntent().getStringExtra("produto_descricao");
        String imagem = getIntent().getStringExtra("produto_imagem");
        int estoque = getIntent().getIntExtra("produto_estoque", 0);
        String loja = getIntent().getStringExtra("produto_loja");
        String categoria = getIntent().getStringExtra("produto_categoria");



        produto = new Produto(produtoId, nome, descricao, preco, imagem, loja, categoria);
        produto.setEstoque(estoque);

        Log.d("DETALHES_DEBUG", "Produto criado - Loja: " + loja);


        // Inicializar views
        ImageView imagemProduto = findViewById(R.id.imagem_produto);
        TextView nomeProduto = findViewById(R.id.nome_produto);
        TextView precoProduto = findViewById(R.id.preco_produto);
        TextView descricaoProduto = findViewById(R.id.descricao_produto);
        TextView estoqueProduto = findViewById(R.id.estoque_produto);
        Button btnAdicionar = findViewById(R.id.btnAdicionarAocarrinho);

        // Preencher os dados
        Glide.with(this)
                .load(produto.getImagem())
                .into(imagemProduto);

        nomeProduto.setText(produto.getNome());
        precoProduto.setText("R$ " + produto.getPreco());
        descricaoProduto.setText(produto.getDescricao());
        estoqueProduto.setText("Estoque: " + produto.getEstoque() + " unidades");

        ImageButton btnCarrinho = findViewById(R.id.goToCartBtn);
        btnCarrinho.setOnClickListener(v -> {
            startActivity(new Intent(this, CarrinhoActivity.class));
        });

        btnAdicionar.setOnClickListener(v -> {
            if (produto.getEstoque() > 0) {
                CartManager.getInstance().addItem(produto);

                Snackbar.make(findViewById(android.R.id.content), produto.getNome() + " adicionado ao carrinho", Snackbar.LENGTH_SHORT)
                        .setAction("Ver Carrinho", view -> {
                            startActivity(new Intent(this, CarrinhoActivity.class));

                        })
                        .setBackgroundTint(getResources().getColor(R.color.purple))
                        .setTextColor(getResources().getColor(android.R.color.white))
                        .setActionTextColor(getResources().getColor(android.R.color.white))
                        .show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Produto sem estoque disponível", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getResources().getColor(R.color.red))
                        .show();
            }
        });
    }
}