package com.example.ordensservico.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Card;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateOrderActivity extends AppCompatActivity {

    private TextInputLayout et_preco;
    private TextInputLayout et_descricao;
    private TextInputLayout et_data_abertura;
    private TextInputLayout et_data_fechamento;
    private Spinner spinner_status;
    private Button btn_alterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        iniciateViews();

        Card card = new Card();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Toast.makeText(this, "Não há ordem para alterar", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateOrderActivity.this, ConsultOrdersActivity.class);
                startActivity(i);
                finish();
            } else {
                card = (Card) extras.getSerializable("CARD_TO_UPDATE");
            }
        } else {
            card = (Card) savedInstanceState.getSerializable("CARD_TO_UPDATE");
        }

        Card cardFinal = card;
        btn_alterar.setOnClickListener(v -> updateClient(cardFinal));

        et_preco.getEditText().setText(cardFinal.getPreco());
        et_data_abertura.getEditText().setText(cardFinal.getAbertura());
        et_data_fechamento.getEditText().setText(cardFinal.getFechamento());
        et_descricao.getEditText().setText(cardFinal.getDescricao());
    }

    private void updateClient(Card cardFinal) {
        if (et_descricao.getEditText().getText().toString().isEmpty() ||
                et_data_fechamento.getEditText().getText().toString().isEmpty() ||
                et_data_abertura.getEditText().getText().toString().isEmpty() ||
                et_preco.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(UpdateOrderActivity.this,
                    "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
        else {
            cardFinal.setPreco(et_preco.getEditText().getText().toString());
            cardFinal.setAbertura(et_data_abertura.getEditText().getText().toString());
            cardFinal.setDescricao(et_descricao.getEditText().getText().toString());
            cardFinal.setFechamento(et_data_fechamento.getEditText().getText().toString());
            cardFinal.setStatus(spinner_status.getSelectedItem().toString());

            FirebaseDatabase.getInstance().getReference("Card").child(String.valueOf(cardFinal.getId()))
                    .setValue(cardFinal).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateOrderActivity.this,
                                    "Card alterado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(
                                    UpdateOrderActivity.this, ConsultOrdersActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UpdateOrderActivity.this,
                                    "Erro ao atualizar card", Toast.LENGTH_SHORT).show();
                        }
            });
        }
    }

    private void iniciateViews() {
        et_preco = findViewById(R.id.et_preco_update);
        et_descricao = findViewById(R.id.et_descricao_update);
        et_data_abertura = findViewById(R.id.et_data_abertura_update);
        et_data_fechamento = findViewById(R.id.et_data_fechamento_update);
        spinner_status = findViewById(R.id.spinner_ordem_update);
        btn_alterar = findViewById(R.id.btn_ordem_update);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateOrderActivity.this, MainActivity.class);
        startActivity(intent);
    }
}