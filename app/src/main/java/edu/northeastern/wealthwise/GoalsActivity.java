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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.northeastern.wealthwise.datamodels.GoalsItem;

public class GoalsActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String uid;
    private RecyclerView recyclerView;
    private GoalsRecyclerViewAdapter recyclerViewAdapter;
    private final List<GoalsItem> goalsItemsList = new ArrayList<>();
    private ImageView goalsView;
    private TextView goalsText;
    private Button prevButton;
    private Button nextButton;
    private TextView monthView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        goalsView = findViewById(R.id.goalsView);
        goalsText = findViewById(R.id.goalsText);
        goalsText.setTextColor(Color.parseColor("#FF8D45"));
        goalsView.setImageResource(R.mipmap.goals_selected_foreground);
        monthView = findViewById(R.id.monthView);
        prevButton = findViewById(R.id.prevBtn);
        prevButton.setOnClickListener(v -> moveMonth(false));
        nextButton = findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(v -> moveMonth(true));

        createGoalsRecyclerView();
        updateGoalsRecyclerViewWithDBGoalAdd();
        updateGoalsRecyclerViewWithDBExpChange();
    }

    private void updateGoalsRecyclerViewWithDBExpChange() {
        goalsItemsList.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        mDatabase.getReference()
                .child("categoryExpenses")
                .child(uid)
                .child(monthView.getText().toString().toUpperCase().replaceAll("\\s", ""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void updateGoalsRecyclerViewWithDBGoalAdd() {
        goalsItemsList.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        mDatabase.getReference()
                .child("goals")
                .child(uid)
                .child(monthView.getText().toString().toUpperCase().replaceAll("\\s", ""))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.getValue() != null) {
                            String category = snapshot.getKey();
                            String goalAmount = snapshot.getValue().toString();

                            mDatabase.getReference()
                                    .child("categoryExpenses")
                                    .child(uid)
                                    .child(monthView.getText().toString().toUpperCase().replaceAll("\\s", ""))
                                    .child(category)
                                    .get()
                                    .addOnCompleteListener(task -> {
                                        GoalsItem newItem = new GoalsItem(goalAmount, category);
                                        Long expense = 0L;
                                        if (task.getResult() != null && task.getResult().getValue() != null) {
                                            expense = (long) task.getResult().getValue();
                                        }

                                        Long amount = Long.parseLong(goalAmount);
                                        newItem.setGoalStatus(expense <= amount);
                                        newItem.setCategoryExpense(String.valueOf(expense));
                                        goalsItemsList.add(0, newItem);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    });
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void createGoalsRecyclerView() {
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerViewGoals);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new GoalsRecyclerViewAdapter(goalsItemsList);
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
        updateGoalsRecyclerViewWithDBGoalAdd();
    }

    public void onClick(View view){
        int clickId = view.getId();
        if(clickId == R.id.addTransactionBtn) {
            startActivity(new Intent(this, AddGoalsActivity.class));
        }
        if(clickId == R.id.transactionView) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if(clickId == R.id.statsView) {
            startActivity(new Intent(this, StatsActivity.class));
        }
        if(clickId == R.id.profileView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}