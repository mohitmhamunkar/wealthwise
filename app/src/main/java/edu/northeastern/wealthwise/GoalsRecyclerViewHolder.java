package edu.northeastern.wealthwise;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GoalsRecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView category;
    public ImageView colorCircle;
    public TextView amount;

    public GoalsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        category = itemView.findViewById(R.id.categoryGoal);
        colorCircle = itemView.findViewById(R.id.circleView);
        amount = itemView.findViewById(R.id.amountGoal);
    }
}
