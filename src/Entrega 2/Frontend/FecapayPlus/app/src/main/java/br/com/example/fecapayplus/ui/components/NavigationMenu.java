package br.com.example.fecapayplus.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import br.com.example.fecapayplus.R;
import br.com.example.fecapayplus.userauth.Auth;
import br.com.example.fecapayplus.utils.UserActions;


public abstract class NavigationMenu extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected TextView txtNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //Componente de menu de navegação lateral
    protected void setupNavigation() {
        Auth auth = new Auth(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNav = findViewById(R.id.userNameNav);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        txtNav = headerView.findViewById(R.id.userNameNav);

        String nome = auth.getNome();
        String sobrenome = auth.getSobrenome();
        txtNav.setText(nome + " " + sobrenome);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavItemSelected);
    }

    //Metodo para navegar entre as activities de cada opção do menu
    private boolean onNavItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if(this instanceof HomeActivity)return true;

            startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.nav_profile) {
            if(this instanceof ProfileActivity)return true;

            startActivity(new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.nav_logout) {
            UserActions.logOut(this);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
