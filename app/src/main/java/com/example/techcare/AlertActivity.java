package com.example.techcare;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AlertActivity extends AppCompatActivity {

    TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        // Link views
        btnBack = findViewById(R.id.btnBack);

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());
    }
}