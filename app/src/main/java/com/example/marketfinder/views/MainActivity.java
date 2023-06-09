package com.example.marketfinder.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogout;
    private Button buttonNovoMercado;
    private Button btnMercados;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes da interface
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonNovoMercado = findViewById(R.id.buttonNovoMarket);
        btnMercados = findViewById(R.id.btnList);
        mAuth = FirebaseAuth.getInstance();

        // Definir o listener de clique para o botão de login
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de login
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                mAuth.signOut();

            }
        });

        // Definir o listener de clique para o botão de criar novo usuário
        buttonNovoMercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MarketDetailActivity.class);
                startActivity(intent);
            }
        });
        btnMercados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarketListAdapter.class);
                startActivity(intent);
            }
        });
    }
}

