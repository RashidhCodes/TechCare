package com.example.techcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etPassword;
    Button btnRegister;
    TextView tvGoLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Link to database
        db = new DatabaseHelper(this);

        // Link views to variables
        etName       = findViewById(R.id.etName);
        etEmail      = findViewById(R.id.etEmail);
        etPhone      = findViewById(R.id.etPhone);
        etPassword   = findViewById(R.id.etPassword);
        btnRegister  = findViewById(R.id.btnRegister);
        tvGoLogin    = findViewById(R.id.tvGoLogin);

        // REGISTER BUTTON
        btnRegister.setOnClickListener(v -> {
            String name     = etName.getText().toString().trim();
            String email    = etEmail.getText().toString().trim();
            String phone    = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // --- VALIDATION ---
            if (name.isEmpty()) {
                etName.setError("Full name is required");
                etName.requestFocus();
                return;
            }
            if (name.length() < 2) {
                etName.setError("Enter a valid full name");
                etName.requestFocus();
                return;
            }
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
            if (phone.isEmpty()) {
                etPhone.setError("Phone number is required");
                etPhone.requestFocus();
                return;
            }
            if (!phone.matches("^[+]?[0-9]{10,13}$")) {
                etPhone.setError("Enter a valid phone (10-13 digits)");
                etPhone.requestFocus();
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

            // --- CHECK DUPLICATE EMAIL ---
            if (db.emailExists(email)) {
                etEmail.setError("This email is already registered");
                etEmail.requestFocus();
                return;
            }

            // --- SAVE TO DATABASE ---
            long result = db.registerUser(name, email, phone, password);
            if (result != -1) {
                Toast.makeText(this,
                        "Account created successfully! Please login.",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this,
                        "Registration failed. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // GO TO LOGIN
        tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}