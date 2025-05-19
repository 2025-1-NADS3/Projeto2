package br.com.example.fecapayplus.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Transacao;
import br.com.example.fecapayplus.data.repository.TransacaoRepository;
import br.com.example.fecapayplus.ui.adapters.TransacaoAdapter;
import br.com.example.fecapayplus.userauth.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransacoesActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    private RecyclerView recyclerView;
    private TransacaoAdapter adapter;
    private TextView textSemItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes);
        EdgeToEdge.enable(this);

        setupNavigation();
        inicializarViews();
        configurarRecyclerView();
        carregarTransacoes();
    }

    private void inicializarViews() {
        recyclerView = findViewById(R.id.recyclerTransacoes);
        textSemItens = findViewById(R.id.textSemItens);
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransacaoAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void carregarTransacoes() {
        Auth auth = new Auth(this);
        int ra = auth.getRa();

        TransacaoRepository repository = new TransacaoRepository();
        repository.getTransacoes(ra, new Callback<List<Transacao>>() {
            @Override
            public void onResponse(Call<List<Transacao>> call, Response<List<Transacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Transacao> transacoes = response.body();
                    if (transacoes.isEmpty()) {
                        textSemItens.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        textSemItens.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.atualizarTransacoes(transacoes);
                    }
                } else {
                    textSemItens.setText("Erro ao carregar transações");
                    textSemItens.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Transacao>> call, Throwable t) {
                textSemItens.setText("Erro na conexão");
                textSemItens.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Log.e("Transacoes", "Erro: " + t.getMessage());
            }
        });

    }
}