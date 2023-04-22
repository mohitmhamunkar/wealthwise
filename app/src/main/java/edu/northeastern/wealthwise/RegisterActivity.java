package edu.northeastern.wealthwise;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

//        username = findViewById(R.id.userName);
//        password = findViewById(R.id.password);

//        Button registerButton = findViewById(R.id.registerBtn);
        Button registerButton = new Button(getApplicationContext());
        registerButton.setOnClickListener(v -> doRegister(username, password));
    }

    private void doRegister(EditText username, EditText password) {
        String userStr = String.valueOf(username.getText());
        String passwordStr = String.valueOf(password.getText());

        if (TextUtils.isEmpty(userStr)) {
            Toast.makeText(RegisterActivity.this, "Please enter Username!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(RegisterActivity.this, "Please enter Password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userStr, passwordStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registration Successful!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}