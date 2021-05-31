package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ordensservico.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ImageView iconExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adicionar botao always on display da toolbar que desloga o user

        clickBotaoConsultarOrdem();
        clickBotaoCadastrarOrdem();
        clickBotaoCadastrarCliente();
        clickBotaoConsultarClientes();

        iconExit = findViewById(R.id.iv_user_exit);

        iconExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void clickBotaoConsultarClientes() {
        Button btn_cliente = findViewById(R.id.btn_consultar_clientes);
        Intent i = new Intent(this, SearchClientsActivity.class);
        btn_cliente.setOnClickListener(v -> startActivity(i));
    }

    private void clickBotaoCadastrarCliente() {
        Button btn_cliente = findViewById(R.id.btn_cadastrar_cliente);
        Intent i = new Intent(this, ClientsRegisterActivity.class);
        btn_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    private void clickBotaoCadastrarOrdem() {
        Button btn_cadastrar = findViewById(R.id.btn_cadastrar_ordem);
        Intent i = new Intent(this, ServiceOrdersActivity.class);
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    private void clickBotaoConsultarOrdem() {
        Button btn_consultar = findViewById(R.id.btn_consultar_ordem);
        Intent i = new Intent(this, ConsultOrdersActivity.class);
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}