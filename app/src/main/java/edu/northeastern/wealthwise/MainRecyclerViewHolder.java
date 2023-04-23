package edu.northeastern.wealthwise;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView date;
    public TextView category;
    public TextView note;
    public TextView amount;
    public TextView accounts;
    public MainRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.date);
        category = itemView.findViewById(R.id.category);
        note = itemView.findViewById(R.id.note);
        amount = itemView.findViewById(R.id.amount);
        accounts = itemView.findViewById(R.id.accounts);
    }
}
