package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class AddGoalsActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String uid;
    private Spinner categorySp;
    private EditText goalAmount;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals);
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        categorySp = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expenseCat_array, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        categorySp.setAdapter(adapter);
        goalAmount = findViewById(R.id.goalsAppAmount);
        saveButton = findViewById(R.id.saveBtnGoals);
        saveButton.setOnClickListener(v -> addGoal());

    }

    private void addGoal() {
        String nodeDate = ""+LocalDate.now().getMonth()+LocalDate.now().getYear();

        mDatabase.getReference().child("goals").child(uid).child(nodeDate)
                .child(categorySp.getSelectedItem().toString())
                .setValue(goalAmount.getText().toString());

        Intent intent = new Intent(getApplicationContext(), GoalsActivity.class);
        startActivity(intent);
        finish();
    }
}