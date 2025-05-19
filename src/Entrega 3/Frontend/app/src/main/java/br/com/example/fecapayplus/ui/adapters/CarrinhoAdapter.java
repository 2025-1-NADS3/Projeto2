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
import br.com.example.fecapayplus.data.model.ItemCarrinho;
import br.com.example.fecapayplus.utils.DinheiroUtils;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {
    private List<ItemCarrinho> itens;
    private OnItemCarrinhoListener listener;

    public interface OnItemCarrinhoListener {
        void onQuantidadeChanged(ItemCarrinho item, int novaQuantidade);
        void onRemoverItem(ItemCarrinho item);
    }

    public CarrinhoAdapter(List<ItemCarrinho> itens, OnItemCarrinhoListener listener) {
        this.itens = itens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCarrinho item = itens.get(position);

        holder.nome.setText(item.getNome());
        holder.preco.setText(DinheiroUtils.format(item.getPreco()));
        holder.quantidade.setText(String.valueOf(item.getQuantidade()));

        Glide.with(holder.itemView.getContext())
                .load(item.getImagemLink())
                .into(holder.imagem);

        holder.btnAumentar.setOnClickListener(v -> {
            int novaQuantidade = item.getQuantidade() + 1;
            listener.onQuantidadeChanged(item, novaQuantidade);
        });

        holder.btnDiminuir.setOnClickListener(v -> {
            if (item.getQuantidade() > 1) {
                int novaQuantidade = item.getQuantidade() - 1;
                listener.onQuantidadeChanged(item, novaQuantidade);
            }
        });

        holder.btnRemover.setOnClickListener(v -> {
            listener.onRemoverItem(item);
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco, quantidade;
        Button btnAumentar, btnDiminuir;
        ImageButton btnRemover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imagem_produto);
            nome = itemView.findViewById(R.id.nome_produto);
            preco = itemView.findViewById(R.id.preco_produto);
            quantidade = itemView.findViewById(R.id.quantidade);
            btnAumentar = itemView.findViewById(R.id.btn_aumentar);
            btnDiminuir = itemView.findViewById(R.id.btn_diminuir);
            btnRemover = itemView.findViewById(R.id.btn_remover);
        }
    }
}