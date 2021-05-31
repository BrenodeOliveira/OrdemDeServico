package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Client;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateClientActivity extends AppCompatActivity {

    private TextInputLayout et_nome;
    private TextInputLayout et_email;
    private TextInputLayout et_tel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        et_nome = findViewById(R.id.et_nome_cliente_alterar_cliente);
        et_email = findViewById(R.id.et_email_cliente_alterar_cliente);
        et_tel = findViewById(R.id.et_telefone_alterar_cliente);

        Client client = new Client();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Toast.makeText(UpdateClientActivity.this, "Não há um cliente para ser alterado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateClientActivity.this, SearchClientsActivity.class);
                startActivity(intent);
                finish();
            } else {
                client = (Client) extras.getSerializable("CLIENT_TO_UPDATE");
            }
        } else {
            client = (Client) savedInstanceState.getSerializable("CLIENT_TO_UPDATE");
        }

        Button updateClient = findViewById(R.id.btn_alterar_cliente);

        Client finalClient = client;
        updateClient.setOnClickListener(v -> updateClient(finalClient));

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_clients_alterar_cliente);
        myToolbar.setTitle("Alterar cliente " + finalClient.getNome());
        setSupportActionBar(myToolbar);

        et_nome.getEditText().setText(finalClient.getNome());
        et_email.getEditText().setText(finalClient.getEmail());
        et_tel.getEditText().setText(finalClient.getTelefone());
    }

    private void updateClient(Client client) {
        if(et_nome.getEditText().getText().toString().isEmpty() || et_email.getEditText().getText().toString().isEmpty() || et_tel.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(UpdateClientActivity.this, "Preencha todos os campos com as informações do cliente", Toast.LENGTH_SHORT).show();
        }
        else {
            client.setNome(et_nome.getEditText().getText().toString());
            client.setEmail(et_email.getEditText().getText().toString());
            client.setTelefone(et_tel.getEditText().getText().toString());

            FirebaseDatabase.getInstance().getReference("Clients")
                    .child(String.valueOf(client.getId()))
                    .setValue(client).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateClientActivity.this, "Cliente alterado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateClientActivity.this, SearchClientsActivity.class);
                    startActivity(intent);
                    finish();
                }  else {
                    Toast.makeText(UpdateClientActivity.this, "Erro ao alterar cliente", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateClientActivity.this, MainActivity.class);
        startActivity(intent);
    }
}