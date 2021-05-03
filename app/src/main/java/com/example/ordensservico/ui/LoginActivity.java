package com.example.ordensservico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private TextInputLayout et_email;
    private TextInputLayout et_password;
    ProgressBar pb;
    Button btn_create;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);

        pb = findViewById(R.id.simple_bar);
        btn_create = findViewById(R.id.btn_create);
        btn_login = findViewById(R.id.btn_register);

        clickBotaoLogin();
        startActivityRegister();
    }

    private void clickBotaoLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void startActivityRegister() {
        Intent i = new Intent(this, RegisterActivity.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    private void signUpUser() {
        String email_login = et_email.getEditText().getText().toString();
        String password_login = et_password.getEditText().getText().toString();

        buttonDisabled();

        if (email_login.isEmpty()) {
            et_email.setError("Insira um e-mail");
            et_email.requestFocus();
            buttonEnabled();
            return;
        }

        if (password_login.isEmpty()) {
            et_password.setError("Insira uma senha");
            et_password.requestFocus();
            buttonEnabled();
            return;
        }

        mAuth.signInWithEmailAndPassword(email_login, password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser email_user = mAuth.getCurrentUser();

                    updateUI(email_user);
                } else {
                    updateUI(null);
                }
            }
        });
    }

    private void buttonDisabled() {
        pb.setVisibility(View.VISIBLE);
        btn_login.setEnabled(false);
        btn_create.setEnabled(false);
    }

    private void buttonEnabled() {
        pb.setVisibility(View.GONE);
        btn_login.setEnabled(true);
        btn_create.setEnabled(true);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Falha ao acessar a sua conta",
                    Toast.LENGTH_SHORT).show();
            buttonEnabled();
        }
    }

}