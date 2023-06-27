package com.example.marketfinder.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.marketfinder.R;
import com.example.marketfinder.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editCreatedUsername;
    private EditText editCreatedPassword;
    private EditText editCreatedName;
    private EditText editCreatedLastName;
    private EditText editConfirmPassword;
    private CheckBox verSenhaCreated;
    private Button buttonSignUpCreated;
    private Button buttonLoginCreated;
    private ProgressBar signUpProgressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflar o layout XML usando LayoutInflater
        View view = LayoutInflater.from(this).inflate(R.layout.activity_sign_up, null);

        // Definir a visualização inflada como o conteúdo da atividade
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        editCreatedName = findViewById(R.id.editCreatedName);
        editCreatedLastName = findViewById(R.id.editCreatedLastName);
        editCreatedUsername = findViewById(R.id.editCreatedUsername);
        editCreatedPassword = findViewById(R.id.editCreatedPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        verSenhaCreated = findViewById(R.id.verSenhaCreated);
        buttonLoginCreated = findViewById(R.id.buttonLoginCreated);
        buttonSignUpCreated = findViewById(R.id.buttonSignUpCreated);
        signUpProgressBar = findViewById(R.id.signUpProgressBar);


        verSenhaCreated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editCreatedPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else{
                    editCreatedPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        buttonSignUpCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = new UserModel();

                userModel.setEmail(editCreatedUsername.getText().toString());
                userModel.setNome(editCreatedName.getText().toString());
                userModel.setSobrenome(editCreatedLastName.getText().toString());
                String password = editCreatedPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();

                if ( !TextUtils.isEmpty(userModel.getNome()) && !TextUtils.isEmpty(userModel.getSobrenome()) && !TextUtils.isEmpty(userModel.getEmail()) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    if (password.equals(confirmPassword)){
                        signUpProgressBar.setVisibility(View.VISIBLE);

                        // Redirecionar para a próxima atividade após o login bem-sucedido

                        mAuth.createUserWithEmailAndPassword(userModel.getEmail(), password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            userModel.setId(mAuth.getUid());
                                            userModel.savar();
                                            abrirTelaPrincipal();
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(SignUpActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                        }
                                        signUpProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                    }else {
                        Toast.makeText(SignUpActivity.this, "a senha deve ser mesma em ambos os campos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        buttonLoginCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void abrirTelaPrincipal() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}