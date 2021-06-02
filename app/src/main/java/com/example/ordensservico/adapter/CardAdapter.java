package com.example.ordensservico.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordensservico.R;
import com.example.ordensservico.model.Card;
import com.example.ordensservico.ui.UpdateOrderActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Card> mListCard;
    private Context context;

    public CardAdapter(List<Card> orders, Context context) {
        mListCard = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_service_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        Card card = mListCard.get(position);

        TextView tvAbertura = holder.abertura;
        tvAbertura.setText(card.getAbertura());

        TextView tvDesc = holder.descricao;
        tvDesc.setText(card.getDescricao());

        TextView tvFechamento = holder.fechamento;
        tvFechamento.setText(card.getFechamento());

        TextView tvPreco = holder.preco;
        tvPreco.setText(card.getPreco());

        TextView tvStatus = holder.status;
        tvStatus.setText(card.getStatus());

        ImageView ivAlterar = holder.ivAlterar;
        ivAlterar.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateOrderActivity.class);
            intent.putExtra("CARD_TO_UPDATE", card);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        ImageView ivDelete = holder.ivDeletar;
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Deseja realmente excluir essa ordem ?");
                builder.setMessage(card.getDescricao());
                builder.setCancelable(true);

                builder.setPositiveButton("Sim",
                        ((dialog, id) -> {
                    card.setStatus("FECHADO");

                    FirebaseDatabase.getInstance().getReference("Card")
                            .child(String.valueOf(card.getId()))
                            .setValue(card).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    mListCard.remove(card);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(v.getRootView().getContext(), "Erro", Toast.LENGTH_SHORT).show();
                                }
                    });
                }));

                builder.setNegativeButton("Não", (dialog, id) -> dialog.cancel());

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() { return mListCard.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView status;
        public TextView preco;
        public TextView abertura;
        public TextView fechamento;
        public TextView descricao;
        public ImageView ivAlterar;
        public ImageView ivDeletar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = (TextView) itemView.findViewById(R.id.tv_status);
            abertura = (TextView) itemView.findViewById(R.id.tv_opening);
            fechamento = (TextView) itemView.findViewById(R.id.tv_closing);
            preco = (TextView) itemView.findViewById(R.id.tv_price);
            descricao = (TextView) itemView.findViewById(R.id.tv_desc_order);
            ivAlterar = (ImageView) itemView.findViewById(R.id.iv_edit_card);
            ivDeletar = (ImageView) itemView.findViewById(R.id.iv_delete_card);
        }
    }
}
