package edu.northeastern.wealthwise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.wealthwise.datamodels.TotalValues;
import edu.northeastern.wealthwise.datamodels.Transaction;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ImageView home;
    private TextView homeText;
    private TextView incAmount;
    private TextView expAmount;
    private TextView totalAmount;

    private String uid;
    private RecyclerView recyclerView;
    private MainRecyclerViewAdapter recyclerViewAdapter;
    private final List<Transaction> transactionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        createTransactionsRecyclerView();
        updateRecyclerViewWithDB();
        updateTotalValues();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                Transaction currentTxn = transactionList.get(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    //Remove from DB
                    mDatabase.getReference()
                            .child("transactions")
                            .child(uid)
                            .child(currentTxn.getTxnId()).removeValue();
                    updateTotalValuesOnDeletion(currentTxn);
                    Snackbar.make(recyclerView, "Transaction Deleted!", Snackbar.LENGTH_LONG)
                            .show();

                    // Remove from recycler view
                    transactionList.remove(position);
                    recyclerViewAdapter.notifyItemRemoved(position);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        incAmount = findViewById(R.id.incomeAmt);
        expAmount = findViewById(R.id.expenseAmt);
        totalAmount = findViewById(R.id.totalAmt);
        home = findViewById(R.id.transactionView);
        homeText = findViewById(R.id.homeText);
        home.setImageResource(R.mipmap.transaction_selected_foreground);
        homeText.setTextColor(Color.parseColor("#FF8D45"));
    }

    private void updateTotalValuesOnDeletion(Transaction txn) {
        final TotalValues[] totalValues = {new TotalValues()};
        DatabaseReference ref = mDatabase.getReference();
        ref.child("totalValues").child(uid).get().addOnCompleteListener(task -> {
            totalValues[0] = task.getResult().getValue(TotalValues.class);
            if (totalValues[0] != null) {
                double prevIncome = totalValues[0].getIncome();
                double prevExpense = totalValues[0].getExpense();

                if (txn.getTxnType().equals("Income")) {
                    totalValues[0].setIncome(prevIncome - txn.getAmount());
                }
                else {
                    totalValues[0].setExpense(prevExpense - txn.getAmount());
                }
                totalValues[0].setTotal(totalValues[0].getIncome() - totalValues[0].getExpense());
            }
            ref.child("totalValues").child(uid).setValue(totalValues[0]);
        });
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

    private void updateRecyclerViewWithDB() {
        mDatabase.getReference().child("transactions").child(uid).addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Transaction newTxn = snapshot.getValue(Transaction.class);
                transactionList.add(0, newTxn);
                recyclerViewAdapter.notifyDataSetChanged();
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

    private void createTransactionsRecyclerView() {
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new MainRecyclerViewAdapter(transactionList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    public void onClick(View view){
        int clickId = view.getId();
        if(clickId == R.id.addTransactionBtn) {
            startActivity(new Intent(this, TransactionActivity.class));
        }
        if(clickId == R.id.statsView) {
            startActivity(new Intent(this, StatsActivity.class));
        }
        if(clickId == R.id.goalsView) {
            startActivity(new Intent(this, GoalsActivity.class));
        }
        if(clickId == R.id.profileView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}