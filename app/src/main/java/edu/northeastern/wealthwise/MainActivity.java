package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView home;
    private TextView homeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.transactionView);
        homeText = findViewById(R.id.homeText);
        home.setImageResource(R.mipmap.transaction_selected_foreground);
        homeText.setTextColor(Color.parseColor("#FF8D45"));
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
            //TODO
        }
    }
}