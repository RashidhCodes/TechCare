package com.example.techcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceDetailActivity extends AppCompatActivity {

    TextView tvDeviceIcon, tvServiceTitle, tvPrice, tvDescription, btnBack;
    Button btnBookNow;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        // Link views
        tvDeviceIcon   = findViewById(R.id.tvDeviceIcon);
        tvServiceTitle = findViewById(R.id.tvServiceTitle);
        tvPrice        = findViewById(R.id.tvPrice);
        tvDescription  = findViewById(R.id.tvDescription);
        btnBack        = findViewById(R.id.btnBack);
        btnBookNow     = findViewById(R.id.btnBookNow);

        // Get category passed from HomeActivity
        category = getIntent().getStringExtra("category");

        // Set content based on category
        switch (category) {
            case "Smartphone":
                tvDeviceIcon.setText("📱");
                tvServiceTitle.setText("Premium Smartphone Repair");
                tvPrice.setText("Base Rate: $29.00");
                tvDescription.setText(
                        "Complete diagnosis and screen replacement service " +
                                "for all smartphone models. Covers battery, " +
                                "charging port and camera repairs.");
                break;

            case "Laptop":
                tvDeviceIcon.setText("💻");
                tvServiceTitle.setText("Premium Laptop Structural Repair");
                tvPrice.setText("Base Rate: $49.00");
                tvDescription.setText(
                        "Complete diagnosis and logic testing for " +
                                "high-performance hardware systems. Covers " +
                                "troubleshooting component logic failures and " +
                                "micro-soldering connections.");
                break;

            case "Smart TV":
                tvDeviceIcon.setText("📺");
                tvServiceTitle.setText("Smart TV Panel Repair");
                tvPrice.setText("Base Rate: $59.00");
                tvDescription.setText(
                        "Full screen panel diagnosis and replacement " +
                                "service for all Smart TV brands. Covers HDMI " +
                                "ports, backlight and software issues.");
                break;

            case "Washer":
                tvDeviceIcon.setText("🧺");
                tvServiceTitle.setText("Washing Machine Service");
                tvPrice.setText("Base Rate: $39.00");
                tvDescription.setText(
                        "Complete washing machine inspection and repair. " +
                                "Covers drum, motor, water inlet valve and " +
                                "electronic control board replacements.");
                break;
        }

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // BOOK NOW → go to Request Form
        btnBookNow.setOnClickListener(v -> {
            Intent i = new Intent(this, RequestFormActivity.class);
            i.putExtra("category", category);
            startActivity(i);
        });
    }
}