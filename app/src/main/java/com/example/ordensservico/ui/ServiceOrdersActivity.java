package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.ordensservico.R;

public class ServiceOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_orders);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_orders);
        myToolbar.setTitle("Criar ordens de serviço");
        setSupportActionBar(myToolbar);
    }
}