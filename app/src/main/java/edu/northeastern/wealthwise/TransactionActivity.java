package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import edu.northeastern.wealthwise.datamodels.Transaction;

public class TransactionActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private Spinner categorySpinner;
    private Spinner accountSpinner;
    private TextView datePicker;
    private Button incomeBtn;
    private Button expenseBtn;
    private String transType;
    private EditText amountValue;
    private EditText noteValue;
    final Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        categorySpinner = findViewById(R.id.categorySpinner);
        accountSpinner = findViewById(R.id.accountSpinner);
        datePicker = findViewById(R.id.datePicker);
        incomeBtn = findViewById(R.id.incomeBtn);
        expenseBtn = findViewById(R.id.expenseBtn);
        transType = "Expense";
        expenseBtn.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        datePicker.setText(c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expenseCat_array, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        categorySpinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> acAdapter = ArrayAdapter.createFromResource(this,
                R.array.account_array, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        accountSpinner.setAdapter(acAdapter);

        amountValue = findViewById(R.id.amountValue);
        noteValue = findViewById(R.id.noteValue);

        Button saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(v -> updateTransaction());


    }

    private void updateTransaction() {
        if(TextUtils.isEmpty(String.valueOf(amountValue.getText()))) {
            Toast.makeText(TransactionActivity.this, "Please enter Amount of transaction!", Toast.LENGTH_LONG).show();
            return;
        }

        String txnType = transType;
        String dot = String.valueOf(datePicker.getText());
        double amt = Double.parseDouble(String.valueOf(amountValue.getText()));
        String cat = String.valueOf(categorySpinner.getSelectedItem());
        String acc = String.valueOf(accountSpinner.getSelectedItem());
        String note = String.valueOf(noteValue.getText());

        DatabaseReference ref = mDatabase.getReference();
        String uid = mAuth.getUid();

        Transaction txn = new Transaction(txnType, dot, amt, cat, acc, note);
        String pushKey = ref.child("transactions").child(uid).push().getKey();
        txn.setTxnId(pushKey);
        ref.child("transactions").child(uid).child(pushKey).setValue(txn);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view) {
        int clickId = view.getId();
        if(clickId == R.id.datePicker) {
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1),
                    year,
                    month,
                    day);
            datePickerDialog.show();
        }
        if(clickId == R.id.expenseBtn) {
            expenseBtn.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            incomeBtn.setTextColor(getResources().getColor(android.R.color.white));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.expenseCat_array, R.layout.spinner_list);
            adapter.setDropDownViewResource(R.layout.spinner_list);
            categorySpinner.setAdapter(adapter);
            transType = "Expense";
        }
        if (clickId == R.id.incomeBtn) {
            incomeBtn.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            expenseBtn.setTextColor(getResources().getColor(android.R.color.white));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.incomeCat_array, R.layout.spinner_list);
            adapter.setDropDownViewResource(R.layout.spinner_list);
            categorySpinner.setAdapter(adapter);
            transType = "Income";
        }
    }
}