package com.example.techcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    TextView tvDevice, tvCategory, tvLogistics, tvDate, tvPrice, btnBack;
    Button btnConfirm;
    DatabaseHelper db;

    String device, fault, logistics, date, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        db = new DatabaseHelper(this);

        // Get data passed from RequestFormActivity
        device    = getIntent().getStringExtra("device");
        fault     = getIntent().getStringExtra("fault");
        logistics = getIntent().getStringExtra("logistics");
        date      = getIntent().getStringExtra("date");
        category  = getIntent().getStringExtra("category");

        // Link views
        tvDevice    = findViewById(R.id.tvDevice);
        tvCategory  = findViewById(R.id.tvCategory);
        tvLogistics = findViewById(R.id.tvLogistics);
        tvDate      = findViewById(R.id.tvDate);
        tvPrice     = findViewById(R.id.tvPrice);
        btnBack     = findViewById(R.id.btnBack);
        btnConfirm  = findViewById(R.id.btnConfirm);

        // Fill summary details
        tvDevice.setText(device);
        tvCategory.setText(category + " Repair");
        tvLogistics.setText(logistics);
        tvDate.setText(date);

        // Set price based on category
        switch (category) {
            case "Smartphone":
                tvPrice.setText("$29.00"); break;
            case "Laptop":
                tvPrice.setText("$49.00"); break;
            case "Smart TV":
                tvPrice.setText("$59.00"); break;
            case "Washer":
                tvPrice.setText("$39.00"); break;
            default:
                tvPrice.setText("$49.00"); break;
        }

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // CONFIRM BUTTON — save to database
        btnConfirm.setOnClickListener(v -> {

            // Get logged in user id from session
            SharedPreferences prefs =
                    getSharedPreferences("session", MODE_PRIVATE);
            int userId = prefs.getInt("userId", -1);

            // Save booking to database
            long bookingId = db.addBooking(
                    userId, device, fault, logistics, date);

            if (bookingId != -1) {
                Toast.makeText(this,
                        "Booking Confirmed! Job #TC-" + bookingId,
                        Toast.LENGTH_LONG).show();

                // Go to Tracking screen
                Intent i = new Intent(this, TrackingActivity.class);
                i.putExtra("bookingId", bookingId);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this,
                        "Booking failed. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}