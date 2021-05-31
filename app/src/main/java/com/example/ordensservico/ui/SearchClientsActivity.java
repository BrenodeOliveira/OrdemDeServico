package com.example.ordensservico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ordensservico.R;
import com.example.ordensservico.adapter.ClientsAdapter;
import com.example.ordensservico.model.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchClientsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClients;
    private FloatingActionButton btn_adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clients);

        recyclerViewClients = findViewById(R.id.recycler_clients);
        btn_adicionar = findViewById(R.id.float_button_add_client);

        searchAllClients();
        openRegisterClient();
    }

    private void openRegisterClient() {
        btn_adicionar.setOnClickListener(v -> {
            Intent it = new Intent(SearchClientsActivity.this, ClientsRegisterActivity.class);
            startActivity(it);
            finish();
        });
    }

    private void searchAllClients() {
        List <Client> listClients = new ArrayList <>();

        FirebaseDatabase.getInstance().getReference("Clients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int numClients = (int) snapshot.getChildrenCount();

                if(numClients < 1) {
                    Toast.makeText(SearchClientsActivity.this, "Não foram encontrados clientes", Toast.LENGTH_SHORT).show();
                }
                for(int i = 1; i <= numClients; i++) {
                    Client client = snapshot.child(String.valueOf(i)).getValue(Client.class);

                    if(client == null) {
                        return;
                    }
                    if(client.isAtivo()) {
                        listClients.add(client);
                    }
                }
                if(listClients.size() < 1) {
                    Toast.makeText(SearchClientsActivity.this, "Não há clientes cadastrados ainda", Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.sort(listClients, (object1, object2) -> object1.getNome().compareTo(object2.getNome()));
                    ClientsAdapter adapter = new ClientsAdapter(listClients, getApplicationContext());
                    recyclerViewClients.setAdapter(adapter);
                    recyclerViewClients.setLayoutManager(new LinearLayoutManager(SearchClientsActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchClientsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}