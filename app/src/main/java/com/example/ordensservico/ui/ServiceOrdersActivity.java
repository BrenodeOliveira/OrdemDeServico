package com.example.ordensservico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Card;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceOrdersActivity extends AppCompatActivity {

    private TextInputLayout et_preco;
    private TextInputLayout et_descricao;
    private TextInputLayout et_data_abertura;
    private TextInputLayout et_data_fechamento;
    private Spinner spinner_status;
    private Button btn_criar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_orders);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_orders);
        myToolbar.setTitle("Criar ordens de serviço");
        setSupportActionBar(myToolbar);

        iniciateViews();

        btn_criar.setOnClickListener(v -> registerCard());
    }

    private void iniciateViews() {
        et_preco = findViewById(R.id.et_preco);
        et_descricao = findViewById(R.id.et_descricao);
        et_data_abertura = findViewById(R.id.et_data_abertura);
        et_data_fechamento = findViewById(R.id.et_data_fechamento);
        spinner_status = findViewById(R.id.spinner_ordem);
        btn_criar = findViewById(R.id.btn_criar_ordem);
    }

    private void registerCard() {
        String preco = et_preco.getEditText().getText().toString();
        String descricao = et_descricao.getEditText().getText().toString();
        String abertura = et_data_abertura.getEditText().getText().toString();
        String fechamento = et_data_fechamento.getEditText().getText().toString();
        String spinner = spinner_status.getSelectedItem().toString();

        if (preco.isEmpty() || descricao.isEmpty() || abertura.isEmpty() || fechamento.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos",
                    Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int idCard = (int) snapshot.getChildrenCount() + 1;
                Card card = new Card(preco, descricao, abertura, fechamento, spinner, idCard);

                FirebaseDatabase.getInstance().getReference("Card").child(String.valueOf(idCard))
                        .setValue(card).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ServiceOrdersActivity.this,
                                        "Ordem de serviço criada", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ServiceOrdersActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(ServiceOrdersActivity.this,
                                        "Erro ao gerar ordem", Toast.LENGTH_SHORT).show();
                            }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}