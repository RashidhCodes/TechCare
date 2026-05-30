package com.example.techcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvGoRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Link to database
        db = new DatabaseHelper(this);

        // Link views to variables
        etEmail      = findViewById(R.id.etEmail);
        etPassword   = findViewById(R.id.etPassword);
        btnLogin     = findViewById(R.id.btnLogin);
        tvGoRegister = findViewById(R.id.tvGoRegister);

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {
            String email    = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // --- VALIDATION ---
            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter a valid email address");
                etEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                etPassword.requestFocus();
                return;
            }

            // --- CHECK DATABASE ---
            Cursor cursor = db.loginUser(email, password);
            if (cursor.moveToFirst()) {
                // Login success — save session
                int userId   = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name  = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));

                SharedPreferences.Editor editor =
                        getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.putInt("userId", userId);
                editor.putString("userName", name);
                editor.apply();

                Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

        // GO TO REGISTER
        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}