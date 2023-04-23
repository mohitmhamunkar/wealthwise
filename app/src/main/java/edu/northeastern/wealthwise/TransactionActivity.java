package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

public class TransactionActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private Spinner accountSpinner;
    private TextView datePicker;
    private Button incomeBtn;
    private Button expenseBtn;
    private String transType;
    final Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
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
    }

    public void onClick(View view) {
        int clickId = view.getId();
        if(clickId == R.id.datePicker) {
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if(clickId == R.id.expenseBtn) {
            expenseBtn.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            incomeBtn.setTextColor(getResources().getColor(android.R.color.white));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.expenseCat_array, R.layout.spinner_list);
            adapter.setDropDownViewResource(R.layout.spinner_list);
            categorySpinner.setAdapter(adapter);
        }
        if (clickId == R.id.incomeBtn) {
            incomeBtn.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            expenseBtn.setTextColor(getResources().getColor(android.R.color.white));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.incomeCat_array, R.layout.spinner_list);
            adapter.setDropDownViewResource(R.layout.spinner_list);
            categorySpinner.setAdapter(adapter);;
        }
    }
}