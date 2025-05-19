package br.com.example.fecapayplus.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Compra;

public class ComprasAdapter extends RecyclerView.Adapter<ComprasAdapter.CompraViewHolder> {

    private final Context context;
    private final List<Compra> compras;

    public ComprasAdapter(Context context, List<Compra> compras) {
        this.context = context;
        this.compras = compras;
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compra, parent, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        Compra compra = compras.get(position);
        Log.d("CompraAdapter", "Imagem: " + compra.getImagemLink());


        // carrega imagem usando gglide
        Glide.with(context)
                .load(compra.getImagemLink())
                .into(holder.imageViewItem);

        holder.textViewNomeItem.setText(compra.getNomeItem());
        holder.textViewLoja.setText(formatLoja(compra.getLoja()));
        holder.textViewData.setText(formatData(compra.getData()));
        holder.textViewPreco.setText(String.format("R$ %.2f", compra.getPreco()));
        holder.textViewQuantidade.setText(String.format("Qtd: %d", compra.getQuantidade()));
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    private String formatLoja(String loja) {
        switch (loja) {
            case "cardapio": return "Cardápio";
            case "papelaria": return "Papelaria";
            case "livraria": return "Livraria";
            case "atletica": return "Atlética";
            default: return loja;
        }
    }

    private String formatData(String data) {
        return data.replace("T", " ").substring(0, 19);
    }

    static class CompraViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        TextView textViewNomeItem;
        TextView textViewLoja;
        TextView textViewData;
        TextView textViewPreco;
        TextView textViewQuantidade;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewNomeItem = itemView.findViewById(R.id.textViewNomeItem);
            textViewLoja = itemView.findViewById(R.id.textViewLoja);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewPreco = itemView.findViewById(R.id.textViewPreco);
            textViewQuantidade = itemView.findViewById(R.id.textViewQuantidade);
        }
    }
}