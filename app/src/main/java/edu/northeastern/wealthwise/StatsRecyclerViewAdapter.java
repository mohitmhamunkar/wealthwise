package edu.northeastern.wealthwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.wealthwise.datamodels.StatsItem;

public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsRecyclerViewHolder>{
    private final List<StatsItem> statsItemList;

    public StatsRecyclerViewAdapter(List<StatsItem> statsItemList) {
        this.statsItemList = statsItemList;
    }

    @NonNull
    @Override
    public StatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses, parent, false);
        return new StatsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsRecyclerViewHolder holder, int position) {
        StatsItem currentItem = statsItemList.get(position);
        holder.amount.setText(currentItem.getAmount());
        holder.category.setText(currentItem.getCategory());
        holder.percentage.setText(currentItem.getPercentage());
    }

    @Override
    public int getItemCount() {
        return statsItemList.size();
    }
}
