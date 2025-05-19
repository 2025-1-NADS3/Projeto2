package br.com.example.fecapayplus.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.data.model.Compra;
import br.com.example.fecapayplus.data.repository.CompraRepository;
import br.com.example.fecapayplus.ui.adapters.ComprasAdapter;
import br.com.example.fecapayplus.userauth.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprasActivity extends br.com.example.fecapayplus.ui.activities.NavigationMenu {

    private RecyclerView recyclerView;
    private TextView textSemItens;
    private CompraRepository compraRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        EdgeToEdge.enable(this);
        setupNavigation();


        recyclerView = findViewById(R.id.recyclerCompras);
        textSemItens = findViewById(R.id.textSemItens);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        int ra = obterRaUsuario();


        compraRepository = new CompraRepository();


        carregarCompras(ra);
    }

    private void carregarCompras(int ra) {
        compraRepository.getCompras(ra, new Callback<List<Compra>>() {
            @Override
            public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compra> compras = response.body();

                    if (compras.isEmpty()) {
                        mostrarListaVazia();
                    } else {
                        mostrarCompras(compras);
                    }
                } else {
                    mostrarListaVazia();
                }
            }

            @Override
            public void onFailure(Call<List<Compra>> call, Throwable t) {
                mostrarListaVazia();
            }
        });
    }

    private void mostrarCompras(List<Compra> compras) {
        ComprasAdapter adapter = new ComprasAdapter(this, compras);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        textSemItens.setVisibility(View.GONE);
    }

    private void mostrarListaVazia() {
        recyclerView.setVisibility(View.GONE);
        textSemItens.setVisibility(View.VISIBLE);
    }

    private int obterRaUsuario() {
        Auth auth = new Auth(this);
        int ra = auth.getRa();
        return ra;
    }
}