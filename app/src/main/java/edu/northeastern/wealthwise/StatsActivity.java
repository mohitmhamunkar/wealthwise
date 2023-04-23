package edu.northeastern.wealthwise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import edu.northeastern.wealthwise.datamodels.TotalValues;

public class StatsActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String uid;
    private PieChart pieChart;
    private ImageView statsView;
    private TextView statsText;
    private TextView incAmount;
    private TextView expAmount;
    private TextView totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        updateTotalValues();

        incAmount = findViewById(R.id.incomeAmt);
        expAmount = findViewById(R.id.expenseAmt);
        totalAmount = findViewById(R.id.totalAmt);
        statsView = findViewById(R.id.statsView);
        statsText = findViewById(R.id.statsText);
        statsText.setTextColor(Color.parseColor("#FF8D45"));
        statsView.setImageResource(R.mipmap.graph_selected_foreground);
        pieChart = findViewById(R.id.piechart);
        
        updatePieChart();
        
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

    private void updateTotalValues() {
        mDatabase.getReference().child("totalValues").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TotalValues values = snapshot.getValue(TotalValues.class);
                if (values != null) {
                    incAmount.setText("$" + values.getIncome());
                    expAmount.setText("$" + values.getExpense());
                    totalAmount.setText("$" + values.getTotal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatePieChart() {
    }

    public void onClick(View view){
        int clickId = view.getId();
        if(clickId == R.id.transactionView) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if(clickId == R.id.goalsView) {
            startActivity(new Intent(this, GoalsActivity.class));
        }
        if(clickId == R.id.profileView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}