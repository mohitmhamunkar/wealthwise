package edu.northeastern.wealthwise;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.wealthwise.datamodels.GoalsItem;

public class GoalsRecyclerViewAdapter extends RecyclerView.Adapter<GoalsRecyclerViewHolder>{
    List<GoalsItem> goalsList;

    public GoalsRecyclerViewAdapter(List<GoalsItem> goalsList) {
        this.goalsList = goalsList;
    }

    @NonNull
    @Override
    public GoalsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goals, parent, false);
        return new GoalsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsRecyclerViewHolder holder, int position) {
        GoalsItem newItem = goalsList.get(position);
        holder.amount.setText("Exp: $"+newItem.getCategoryExpense()+" | Goal: $"+newItem.getAmount());
        holder.category.setText(newItem.getCategory());
        if (!newItem.isGoalStatus()) {
            holder.colorCircle.setImageResource(R.drawable.circle_goals_red);
        }
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }
}
