package edu.northeastern.wealthwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GoalsActivity extends AppCompatActivity {
    private ImageView goalsView;
    private TextView goalsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        goalsView = findViewById(R.id.goalsView);
        goalsText = findViewById(R.id.goalsText);
        goalsText.setTextColor(Color.parseColor("#FF8D45"));
        goalsView.setImageResource(R.mipmap.goals_selected_foreground);
    }
    public void onClick(View view){
        int clickId = view.getId();
        if(clickId == R.id.addTransactionBtn) {
            //TODO
        }
        if(clickId == R.id.transactionView) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if(clickId == R.id.statsView) {
            startActivity(new Intent(this, StatsActivity.class));
        }
        if(clickId == R.id.profileView) {
            //TODO
        }
    }
}