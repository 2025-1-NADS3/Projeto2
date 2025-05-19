package br.com.example.fecapayplus.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Transacao;
import br.com.example.fecapayplus.utils.Formatters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder> {

    private List<Transacao> transacoes;

    public TransacaoAdapter(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    @NonNull
    @Override
    public TransacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transacao, parent, false);
        return new TransacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacaoViewHolder holder, int position) {
        Transacao transacao = transacoes.get(position);


        int color;
        switch (transacao.getTipoTransacao()) {
            case "RECARGA":
                color = R.color.green;
                break;
            default:
                color = R.color.red;
        }

        holder.tipoTransacao.setText(transacao.getTipoTransacao());

        // Formatando o valor
        try {
            double valor = Double.parseDouble(transacao.getValor());
            holder.valorTransacao.setText(Formatters.formatedBalance(valor));
        } catch (NumberFormatException e) {
            holder.valorTransacao.setText("R$ " + transacao.getValor());
        }
        holder.valorTransacao.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), color));

        // Formatando a data com hora
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = inputFormat.parse(transacao.getDataTransacao());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            holder.dataTransacao.setText(outputFormat.format(date));
        } catch (ParseException e) {
            holder.dataTransacao.setText(transacao.getDataTransacao());
        }
    }

    @Override
    public int getItemCount() {
        return transacoes.size();
    }

    public void atualizarTransacoes(List<Transacao> novasTransacoes) {
        transacoes = novasTransacoes;
        notifyDataSetChanged();
    }

    static class TransacaoViewHolder extends RecyclerView.ViewHolder {
        TextView tipoTransacao;
        TextView valorTransacao;
        TextView dataTransacao;

        public TransacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoTransacao = itemView.findViewById(R.id.tipoTransacao);
            valorTransacao = itemView.findViewById(R.id.valorTransacao);
            dataTransacao = itemView.findViewById(R.id.dataTransacao);
        }
    }
}