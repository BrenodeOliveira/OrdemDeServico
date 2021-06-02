package com.example.ordensservico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.example.ordensservico.adapter.CardAdapter;
import com.example.ordensservico.model.Card;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsultOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_orders);

        recyclerViewCards = findViewById(R.id.recycler_consult_orders);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Consultar ordens de serviço");
        setSupportActionBar(myToolbar);

        searchAllCards();
    }

    private void searchAllCards() {

        List<Card> listCard = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int numCards = (int) snapshot.getChildrenCount();

                if (numCards < 1) {
                    Toast.makeText(ConsultOrdersActivity.this,
                            "Não foram encontrados cards", Toast.LENGTH_SHORT).show();
                }
                for (int i = 1; i <= numCards; i++) {
                    Card card = snapshot.child(String.valueOf(i)).getValue(Card.class);

                    if (card == null) {
                        return;
                    }

                    if (card.getStatus().equals("ABERTO") ||
                            card.getStatus().equals("PENDENTE")) {
                        listCard.add(card);
                    }
                }
                if (listCard.size() < 1) {
                    Toast.makeText(ConsultOrdersActivity.this,
                            "Não há ordens cadastradas", Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.sort(listCard, (object1, object2) ->
                            object1.getStatus().compareTo(object2.getStatus()));
                    //Implementar as funções do recycler com adapter
                    CardAdapter adapter = new CardAdapter(listCard, getApplicationContext());
                    recyclerViewCards.setAdapter(adapter);
                    recyclerViewCards.setLayoutManager(
                            new LinearLayoutManager(ConsultOrdersActivity.this));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}