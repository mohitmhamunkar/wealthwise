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
import com.google.firebase.auth.UserProfileChangeRequest;

import android.view.View;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName;
    EditText username;
    EditText password;
    EditText confirmPasswd;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.name);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.pass);
        confirmPasswd = findViewById(R.id.confirmPass);

        Button registerButton = findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(v -> doRegister(fullName, username, password, confirmPasswd));
    }

    private void doRegister(EditText fullName, EditText username, EditText password, EditText confirmPasswd) {
        String fullNameStr = String.valueOf(fullName.getText());
        String userStr = String.valueOf(username.getText());
        String passwordStr = String.valueOf(password.getText());
        String confirmPwdStr = String.valueOf(confirmPasswd.getText());

        if (!passwordStr.equals(confirmPwdStr)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(fullNameStr)) {
            Toast.makeText(RegisterActivity.this, "Please enter Name!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(userStr)) {
            Toast.makeText(RegisterActivity.this, "Please enter Email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(RegisterActivity.this, "Please enter Password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userStr, passwordStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        Log.d(TAG, "createUserWithEmail:success");

                        // Update user's full name
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullNameStr)
                                .build();
                        if (user != null) {
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    });
                        }

                        Toast.makeText(RegisterActivity.this, "Registration Successful!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed! : " +
                                Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClickLoginRegisterLink(View view) {
        int clickId = view.getId();
        if(clickId == R.id.loginLink) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(clickId == R.id.loginBtn) {
            Toast.makeText(this, "Register Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}