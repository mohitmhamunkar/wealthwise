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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import edu.northeastern.wealthwise.datamodels.TotalValues;
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
        int month = c.get(Calendar.MONTH)+1;
        String monthStr = ""+month;
        if (month < 9) {
            monthStr = "0" + month;
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        String dayStr = ""+day;
        if (day < 10) {
            dayStr = "0" + day;
        }
        datePicker.setText(monthStr + "/" + dayStr + "/" + c.get(Calendar.YEAR));
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
        saveButton.setOnClickListener(v -> {
            try {
                updateTransaction();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void updateTransaction() throws ParseException {
        if(TextUtils.isEmpty(String.valueOf(amountValue.getText()))) {
            Toast.makeText(TransactionActivity.this, "Please enter Amount of transaction!", Toast.LENGTH_LONG).show();
            return;
        }

        String txnType = transType;

        //Creating node string for date
        String dot = String.valueOf(datePicker.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dot, formatter);
        String nodeDate = date.getMonth().toString()+date.getYear();

        double amt = Double.parseDouble(String.valueOf(amountValue.getText()));
        String cat = String.valueOf(categorySpinner.getSelectedItem());
        String acc = String.valueOf(accountSpinner.getSelectedItem());
        String note = String.valueOf(noteValue.getText());

        DatabaseReference ref = mDatabase.getReference();
        String uid = mAuth.getUid();
        final TotalValues[] totalValues = {new TotalValues()};
        Transaction txn = new Transaction(txnType, dot, amt, cat, acc, note);
        ref.child("totalValues").child(uid).child(nodeDate).get().addOnCompleteListener(task -> {
            totalValues[0] = task.getResult().getValue(TotalValues.class);
            if (totalValues[0] != null) {
                double prevIncome = totalValues[0].getIncome();
                double prevExpense = totalValues[0].getExpense();

                if (txnType.equals("Income")) {
                    totalValues[0].setIncome(prevIncome + amt);
                }
                else {
                    totalValues[0].setExpense(prevExpense + amt);
                }
                totalValues[0].setTotal(totalValues[0].getIncome() - totalValues[0].getExpense());
            }
            else {
                if (txnType.equals("Income")) {
                    totalValues[0] = new TotalValues(amt, 0 , amt);
                }
                else {
                    totalValues[0] = new TotalValues(0, amt , amt);
                }

            }
            ref.child("totalValues").child(uid).child(nodeDate).setValue(totalValues[0]);
        });
        String pushKey = ref.child("transactions").child(uid).child(nodeDate).push().getKey();
        txn.setTxnId(pushKey);
        ref.child("transactions").child(uid).child(nodeDate).child(pushKey).setValue(txn);

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
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        monthOfYear++;
                        String monthStr = ""+monthOfYear;
                        if (monthOfYear < 10) {
                            monthStr = "0" + monthOfYear;
                        }
                        String dayStr = ""+dayOfMonth;
                        if (dayOfMonth < 10) {
                            dayStr = "0" + dayOfMonth;
                        }
                        datePicker.setText(monthStr + "/" + dayStr + "/" + year1);
                    },
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