package com.example.ordensservico.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Client;
import com.example.ordensservico.ui.SearchClientsActivity;
import com.example.ordensservico.ui.UpdateClientActivity;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {

    private List <Client> mClients;
    private Context context;

    public ClientsAdapter(List<Client> contacts, Context context) {
        mClients = contacts;
        this.context = context;
    }

    @Override
    public ClientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.client_search_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClientsAdapter.ViewHolder holder, int position) {
        Client client = mClients.get(position);

        TextView tvNome = holder.nomeCliente;
        tvNome.setText(client.getNome());

        TextView tvEmail = holder.emailCliente;
        tvEmail.setText(client.getEmail());

        TextView tvTel = holder.telCliente;
        tvTel.setText(client.getTelefone());

        ImageView ivAlterar = holder.ivAlterar;
        ivAlterar.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateClientActivity.class);
            intent.putExtra("CLIENT_TO_UPDATE", client);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        ImageView ivDeletar = holder.ivDeletar;
        ivDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getRootView().getContext());
                builder1.setTitle("Deseja realmente excluir esse cliente?");
                builder1.setMessage(client.toString());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sim",
                        (dialog, id) -> {
                            client.setAtivo(false);

                            FirebaseDatabase.getInstance().getReference("Clients")
                                    .child(String.valueOf(client.getId()))
                                    .setValue(client).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    mClients.remove(client);
                                    notifyItemRemoved(position);
                                }  else {
                                    Toast.makeText(v.getRootView().getContext(), "Erro ao alterar cliente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                builder1.setNegativeButton(
                        "Não",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mClients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCliente;
        public TextView emailCliente;
        public TextView telCliente;
        public ImageView ivAlterar;
        public ImageView ivDeletar;

        public ViewHolder(View itemView) {
            super(itemView);

            nomeCliente = (TextView) itemView.findViewById(R.id.tv_client_name_item);
            emailCliente = (TextView) itemView.findViewById(R.id.tv_client_email_item);
            telCliente = (TextView) itemView.findViewById(R.id.tv_client_phone_item);
            ivAlterar = (ImageView) itemView.findViewById(R.id.iv_edit_client_item);
            ivDeletar = (ImageView) itemView.findViewById(R.id.iv_delete_client_item);
        }
    }
}
