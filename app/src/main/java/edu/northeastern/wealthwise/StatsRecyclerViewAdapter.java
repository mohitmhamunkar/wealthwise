package edu.northeastern.wealthwise;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.wealthwise.datamodels.StatsItem;

public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsRecyclerViewHolder>{
    private final List<StatsItem> statsItemList;
    private Map<String, String> colorMap;

    public StatsRecyclerViewAdapter(List<StatsItem> statsItemList) {
        this.statsItemList = statsItemList;
        colorMap = new HashMap<>();
        initColorMap();
    }

    private void initColorMap() {
        colorMap.put("Food", "#e60049");
        colorMap.put("Social Life", "#0bb4ff");
        colorMap.put("Pets", "#50e991");
        colorMap.put("Transport", "#e6d800");
        colorMap.put("Culture", "#9b19f5");
        colorMap.put("Household", "#ffa300");
        colorMap.put("Apparel", "#dc0ab4");
        colorMap.put("Beauty", "#b3d4ff");
        colorMap.put("Health", "#00bfa0");
        colorMap.put("Education", "#b33dc6");
        colorMap.put("Gift", "#87bc45");
        colorMap.put("Other", "#f46a9b");
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
        holder.amount.setText("$"+currentItem.getAmount());
        holder.category.setText(currentItem.getCategory());
        String perc = currentItem.getPercentage();
        if (perc.length() > 4) {
            perc = perc.substring(0,4);
        }
        holder.percentage.setText(perc+"%");
        holder.percentage.setBackgroundColor(Color.parseColor(colorMap.get(currentItem.getCategory())));
    }

    @Override
    public int getItemCount() {
        return statsItemList.size();
    }
}
