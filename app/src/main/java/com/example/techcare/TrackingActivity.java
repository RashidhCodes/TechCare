package com.example.techcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TrackingActivity extends AppCompatActivity {

    TextView tvJobCode, btnBack;
    Button btnGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Link views
        tvJobCode  = findViewById(R.id.tvJobCode);
        btnBack    = findViewById(R.id.btnBack);
        btnGoHome  = findViewById(R.id.btnGoHome);

        // Get booking ID passed from OrderSummaryActivity
        long bookingId = getIntent().getLongExtra("bookingId", 0);
        tvJobCode.setText("Job Code: #TC-" + bookingId);

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // GO HOME BUTTON — clears back stack
        btnGoHome.setOnClickListener(v -> {
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
    }
}