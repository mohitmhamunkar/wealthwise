package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddGoalsActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expenseCat_array, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        categorySpinner.setAdapter(adapter);
    }

    public void onClick(View view) {
        int clickId = view.getId();
        if(clickId == R.id.saveBtn) {
            startActivity(new Intent(this, GoalsActivity.class));
        }
    }
}