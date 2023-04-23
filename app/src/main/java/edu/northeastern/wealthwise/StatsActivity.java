package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class StatsActivity extends AppCompatActivity {
    private PieChart pieChart;
    private ImageView statsView;
    private TextView statsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statsView = findViewById(R.id.statsView);
        statsText = findViewById(R.id.statsText);
        statsText.setTextColor(Color.parseColor("#FF8D45"));
        statsView.setImageResource(R.mipmap.graph_selected_foreground);
        pieChart = findViewById(R.id.piechart);
        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        40,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        30,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        5,
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        25,
                        Color.parseColor("#29B6F6")));
        pieChart.startAnimation();
        pieChart.setLabelFor(0);
    }
}