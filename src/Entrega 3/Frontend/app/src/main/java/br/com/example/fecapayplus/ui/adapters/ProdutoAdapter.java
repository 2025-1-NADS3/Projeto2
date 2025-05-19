package br.com.example.fecapayplus.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Produto;
import br.com.example.fecapayplus.ui.activities.AtleticaActivity;
import br.com.example.fecapayplus.utils.DinheiroUtils;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {
    private List<Produto> produtos;
    private OnProdutoClickListener listener;

    public interface OnProdutoClickListener {
        void onDetalhesClick(Produto produto);
        void onAddToCartClick(Produto produto);
    }

    public ProdutoAdapter(List<Produto> produtos, OnProdutoClickListener listener) {
        this.produtos = produtos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.preco.setText(DinheiroUtils.format(produto.getPreco()));

        Glide.with(holder.itemView.getContext())
                .load(produto.getImagem())
                .into(holder.imagem);


        if (produto.getEstoque() <= 0) {
            holder.btnAdicionar.setEnabled(false);
            holder.btnAdicionar.setAlpha(0.5f);
        }

        holder.btnDetalhes.setOnClickListener(v -> {
            listener.onDetalhesClick(produto);
        });

        holder.btnAdicionar.setOnClickListener(v -> {
            if (produto.getEstoque() > 0) {
                listener.onAddToCartClick(produto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco;
        Button btnDetalhes;
        ImageButton btnAdicionar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imageProduto);
            nome = itemView.findViewById(R.id.textNome);
            preco = itemView.findViewById(R.id.textPreco);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
            btnAdicionar = itemView.findViewById(R.id.addToCartButton);
        }
    }
}