package edu.northeastern.wealthwise;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(v -> doLogin(username, password));
    }

    private void doLogin(EditText username, EditText password) {
        String userStr = String.valueOf(username.getText());
        String passwordStr = String.valueOf(password.getText());

        if (TextUtils.isEmpty(userStr)) {
            Toast.makeText(LoginActivity.this, "Please enter Username!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(LoginActivity.this, "Please enter Password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(userStr, passwordStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.getDisplayName()!= null) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Welcome " + user.getDisplayName().split(" ")[0],
                                    Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "signInWithEmail:success");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Login failed! : " +
                                        Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void onClickLoginRegisterLink(View view) {
        int clickId = view.getId();
        if(clickId == R.id.registerLink) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
        if(clickId == R.id.loginBtn) {
            Toast.makeText(this, "Register Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}