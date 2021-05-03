package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ordensservico.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Ajustar a tela entrar direto com o usuario

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI(currentUser);
            }
        }, SPLASH_TIME_OUT);
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        Intent d = new Intent(SplashActivity.this, MainActivity.class);

        if (currentUser != null) {
            startActivity(d);
        } else {
            startActivity(i);
        }

        finish();
    }

}