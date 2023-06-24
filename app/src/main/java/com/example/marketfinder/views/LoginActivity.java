package com.example.marketfinder.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marketfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;
    private CheckBox verSenha;
    private ProgressBar loginProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar o layout XML usando LayoutInflater
        View view = LayoutInflater.from(this).inflate(R.layout.activity_login, null);

        // Definir a visualização inflada como o conteúdo da atividade
        setContentView(view);

        // Inicializar os componentes da interface
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        verSenha = findViewById(R.id.verSenha);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        loginProgressBar = findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();

        // Definir o listener de clique para o botão de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os valores digitados pelo usuário
                String email = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Realizar a autenticação (adapte esse código de acordo com a lógica de autenticação do seu app)
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    // Redirecionar para a próxima atividade após o login bem-sucedido
                    loginProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        abrirTelaPrincipal();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                        loginProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        verSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else{
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }



    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
