package edu.northeastern.wealthwise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileView;
    private TextView profileText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileView = findViewById(R.id.profileView);
        profileText = findViewById(R.id.profileText);
        profileText.setTextColor(Color.parseColor("#FF8D45"));
        profileView.setImageResource(R.mipmap.profile_selected_foreground);
    }

    public void onClick(View view){
        int clickId = view.getId();
        if(clickId == R.id.transactionView) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if(clickId == R.id.goalsView) {
            startActivity(new Intent(this, GoalsActivity.class));
        }
        if(clickId == R.id.statsView) {
            startActivity(new Intent(this, StatsActivity.class));
        }
        if(clickId == R.id.changePassBtn) {
            DialogBox(view);
        }
        if(clickId == R.id.logoutBtn) {

        }
    }

    private void DialogBox(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.password_prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        EditText newPass = (EditText) promptView.findViewById(R.id.newPass);
        EditText conPass = (EditText) promptView.findViewById(R.id.conPass);
        Snackbar error = Snackbar.make(view, "Empty Password", Snackbar.LENGTH_LONG).setAction
                ("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogBox(view);
                    }
                });
        Snackbar errorMismatch = Snackbar.make(view, "Password Doesn't Match", Snackbar.LENGTH_LONG).setAction
                ("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogBox(view);
                    }
                });
        Snackbar cancel = Snackbar.make(view, "Operation Cancelled", Snackbar.LENGTH_LONG);
        Snackbar success = Snackbar.make(view, "Password Sucessfully", Snackbar.LENGTH_LONG);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (TextUtils.isEmpty(newPass.getText().toString()) ||
                                TextUtils.isEmpty(conPass.getText().toString())) {
                            //pop a snackbar
                            error.show();
                        } else if (conPass.getText().toString() != newPass.getText().toString()) {
                            //pop a snackbar
                            errorMismatch.show();
                        } else {
                            success.show();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                cancel.show();
                            }
                        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
}