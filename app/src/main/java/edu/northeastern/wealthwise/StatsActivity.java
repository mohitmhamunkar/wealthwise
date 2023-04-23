package edu.northeastern.wealthwise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.northeastern.wealthwise.datamodels.StatsItem;
import edu.northeastern.wealthwise.datamodels.TotalValues;
import edu.northeastern.wealthwise.datamodels.Transaction;

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

    private Button prevButton;
    private Button nextButton;
    private TextView monthView;
    private RecyclerView recyclerView;
    private StatsRecyclerViewAdapter recyclerViewAdapter;
    private final List<StatsItem> statsItemsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        monthView = findViewById(R.id.monthView);
        prevButton = findViewById(R.id.prevBtn);
        prevButton.setOnClickListener(v -> moveMonth(false));
        nextButton = findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(v -> moveMonth(true));
        incAmount = findViewById(R.id.incomeAmt);
        expAmount = findViewById(R.id.expenseAmt);
        totalAmount = findViewById(R.id.totalAmt);
        statsView = findViewById(R.id.statsView);
        statsText = findViewById(R.id.statsText);
        statsText.setTextColor(Color.parseColor("#FF8D45"));
        statsView.setImageResource(R.mipmap.graph_selected_foreground);
        pieChart = findViewById(R.id.piechart);

        createStatsRecyclerView();
        updateTotalValues();
        updateStatsRecyclerViewWithDB();
        updatePieChart();
    }

    private void updateStatsRecyclerViewWithDB() {
        statsItemsList.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        mDatabase.getReference()
                .child("transactions")
                .child(uid)
                .child(monthView.getText().toString().toUpperCase().replaceAll("\\s", ""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double totalExpense = 0;
                        for (DataSnapshot txnSnapshot: snapshot.getChildren()) {
                            Transaction txn = txnSnapshot.getValue(Transaction.class);
                            if (txn.getTxnType().equals("Expense")) {
                                totalExpense += txn.getAmount();
                            }
                        }
                        for (DataSnapshot txnSnapshot: snapshot.getChildren()) {
                            Transaction txn = txnSnapshot.getValue(Transaction.class);
                            if (txn.getTxnType().equals("Expense")) {
                                String percentage = String.valueOf((txn.getAmount()/totalExpense)*100);
                                String category = String.valueOf(txn.getTxnCategory());
                                String amount = String.valueOf(txn.getAmount());
                                StatsItem newItem = new StatsItem(percentage, category, amount);
                                statsItemsList.add(0, newItem);
                            }
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void createStatsRecyclerView() {
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerViewStats);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new StatsRecyclerViewAdapter(statsItemsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    private void moveMonth(boolean up) {
        String currentMonth = monthView.getText().toString().split(" ")[0];
        String currentYear = monthView.getText().toString().split(" ")[1];
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(currentYear), Month.valueOf(currentMonth.toUpperCase()).getValue()-1, LocalDate.now().getDayOfMonth());
        if (c.get(Calendar.MONTH) == Calendar.DECEMBER && up) {
            c.set(Calendar.MONTH, Calendar.JANUARY);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
        }
        else if (c.get(Calendar.MONTH) == Calendar.JANUARY && !up) {
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        } else {
            c.roll(Calendar.MONTH, up);
        }
        String newMonth = new SimpleDateFormat("MMMM").format(c.getTime());
        monthView.setText(newMonth +" "+ c.get(Calendar.YEAR));
        updateTotalValues();
        updateStatsRecyclerViewWithDB();
    }

    private void updateTotalValues() {
        incAmount.setText("");
        expAmount.setText("");
        totalAmount.setText("");
        mDatabase.getReference().child("totalValues").child(uid).child(monthView.getText().toString().toUpperCase().replaceAll("\\s", "")).addValueEventListener(new ValueEventListener() {
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