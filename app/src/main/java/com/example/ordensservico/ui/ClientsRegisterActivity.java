package com.example.ordensservico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientsRegisterActivity extends AppCompatActivity {

    private TextInputLayout et_nome;
    private TextInputLayout et_email;
    private TextInputLayout et_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_register);

        et_nome = findViewById(R.id.et_nome_cliente);
        et_email = findViewById(R.id.et_email_cliente);
        et_tel = findViewById(R.id.et_telefone);

        Button registerClient = findViewById(R.id.btn_criar_cliente);

        registerClient.setOnClickListener(v -> registerClient());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_clients);
        myToolbar.setTitle("Cadastrar clientes");
        setSupportActionBar(myToolbar);
    }

    private void registerClient() {
        String nome = et_nome.getEditText().getText().toString();
        String email = et_email.getEditText().getText().toString();
        String telefone = et_tel.getEditText().getText().toString();

        if(nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(ClientsRegisterActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Clients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int idClient = (int) snapshot.getChildrenCount() + 1;
                Client client = new Client(nome, email, telefone, idClient);

                FirebaseDatabase.getInstance().getReference("Clients")
                        .child(String.valueOf(idClient))
                        .setValue(client).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ClientsRegisterActivity.this, "Cliente criado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClientsRegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }  else {
                                Toast.makeText(ClientsRegisterActivity.this, "Erro ao criar cliente", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}