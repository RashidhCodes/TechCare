package com.example.techcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvGreeting;
    LinearLayout cardSmartphone, cardLaptop, cardTV, cardWasher;
    LinearLayout navBookings, navSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Load username from session
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String userName = prefs.getString("userName", "User");

        // Link views
        tvGreeting    = findViewById(R.id.tvGreeting);
        cardSmartphone = findViewById(R.id.cardSmartphone);
        cardLaptop    = findViewById(R.id.cardLaptop);
        cardTV        = findViewById(R.id.cardTV);
        cardWasher    = findViewById(R.id.cardWasher);
        navBookings   = findViewById(R.id.navBookings);
        TextView navAlert = findViewById(R.id.tvAlertBell);
        navSupport    = findViewById(R.id.navSupport);

        // Set greeting
        tvGreeting.setText("Hello, " + userName + " 👋");

        // Category cards → go to Service Detail
        cardSmartphone.setOnClickListener(v -> goToDetail("Smartphone"));
        cardLaptop.setOnClickListener(v -> goToDetail("Laptop"));
        cardTV.setOnClickListener(v -> goToDetail("Smart TV"));
        cardWasher.setOnClickListener(v -> goToDetail("Washer"));

        // Bottom nav
        navBookings.setOnClickListener(v ->
                startActivity(new Intent(this, TrackingActivity.class)));
        navSupport.setOnClickListener(v ->
                 startActivity(new Intent(this, SupportActivity.class)));
        findViewById(R.id.tvAlertBell).setOnClickListener(v ->
                startActivity(new Intent(this, AlertActivity.class)));
    }

    private void goToDetail(String category) {
        Intent i = new Intent(this, ServiceDetailActivity.class);
        i.putExtra("category", category);
        startActivity(i);
    }
}