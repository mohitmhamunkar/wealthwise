package edu.northeastern.wealthwise;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatsRecyclerViewHolder extends RecyclerView.ViewHolder{
    public Button percentage;
    public TextView category;
    public TextView amount;
    public StatsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        percentage = itemView.findViewById(R.id.percentage);
        category = itemView.findViewById(R.id.categoryExpense);
        amount = itemView.findViewById(R.id.amountExpense);
    }
}
