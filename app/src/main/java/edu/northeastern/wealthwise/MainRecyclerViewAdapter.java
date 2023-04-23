package edu.northeastern.wealthwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.wealthwise.datamodels.Transaction;

public class MainRecyclerViewAdapter  extends RecyclerView.Adapter<MainRecyclerViewHolder>{
    private final List<Transaction> transactionList;

    public MainRecyclerViewAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transactions, parent, false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        Transaction currentTxn = transactionList.get(position);
        holder.date.setText(currentTxn.getDateOfTransaction());
        holder.category.setText(currentTxn.getTxnCategory());
        holder.accounts.setText(currentTxn.getAccountCategory());
        holder.amount.setText("$"+ currentTxn.getAmount());
        if (!currentTxn.getNote().equals("")) {
            holder.note.setText("Note: " + currentTxn.getNote());
        }
        else {
            holder.note.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
