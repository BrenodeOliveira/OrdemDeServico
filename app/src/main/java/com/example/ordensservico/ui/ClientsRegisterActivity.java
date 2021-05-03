package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.ordensservico.R;

public class ClientsRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_register);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_clients);
        myToolbar.setTitle("Cadastrar clientes");
        setSupportActionBar(myToolbar);
    }
}