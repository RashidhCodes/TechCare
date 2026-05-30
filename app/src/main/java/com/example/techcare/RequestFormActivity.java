package com.example.techcare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class RequestFormActivity extends AppCompatActivity {

    Spinner spinnerDevice;
    EditText etFault, etDate;
    RadioGroup radioLogistics;
    RadioButton rbPickup;
    Button btnNext;
    TextView btnBack;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        // Get category from ServiceDetailActivity
        category = getIntent().getStringExtra("category");

        // Link views
        spinnerDevice   = findViewById(R.id.spinnerDevice);
        etFault         = findViewById(R.id.etFault);
        etDate          = findViewById(R.id.etDate);
        radioLogistics  = findViewById(R.id.radioLogistics);
        rbPickup        = findViewById(R.id.rbPickup);
        btnNext         = findViewById(R.id.btnNext);
        btnBack         = findViewById(R.id.btnBack);

        // Setup device spinner based on category
        String[] devices;
        switch (category) {
            case "Smartphone":
                devices = new String[]{"iPhone 15 Pro", "Samsung Galaxy S24",
                        "Google Pixel 8", "OnePlus 12"};
                break;
            case "Laptop":
                devices = new String[]{"MacBook Pro 16\"", "Dell XPS 15",
                        "HP Spectre x360", "Lenovo ThinkPad"};
                break;
            case "Smart TV":
                devices = new String[]{"Samsung QLED 55\"", "LG OLED 55\"",
                        "Sony Bravia 65\"", "TCL 50\""};
                break;
            default:
                devices = new String[]{"Samsung Washer", "LG Washer",
                        "Whirlpool Washer", "Bosch Washer"};
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, devices);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDevice.setAdapter(adapter);

        // Date picker on tap
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year  = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day   = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        String date = d + "/" + (m + 1) + "/" + y;
                        etDate.setText(date);
                    }, year, month, day);

            // Only allow future dates
            dialog.getDatePicker().setMinDate(
                    System.currentTimeMillis() - 1000);
            dialog.show();
        });

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // NEXT BUTTON with validation
        btnNext.setOnClickListener(v -> {
            String device    = spinnerDevice.getSelectedItem().toString();
            String fault     = etFault.getText().toString().trim();
            String date      = etDate.getText().toString().trim();
            String logistics = rbPickup.isChecked() ?
                    "Home Pickup" : "Drop-off at Depot";

            // --- VALIDATION ---
            if (fault.isEmpty()) {
                etFault.setError("Please describe the fault");
                etFault.requestFocus();
                return;
            }
            if (fault.length() < 10) {
                etFault.setError("Please provide more detail (min 10 chars)");
                etFault.requestFocus();
                return;
            }
            if (date.isEmpty()) {
                etDate.setError("Please select an appointment date");
                etDate.requestFocus();
                return;
            }

            // Pass data to Order Summary
            Intent i = new Intent(this, OrderSummaryActivity.class);
            i.putExtra("device",    device);
            i.putExtra("fault",     fault);
            i.putExtra("logistics", logistics);
            i.putExtra("date",      date);
            i.putExtra("category",  category);
            startActivity(i);
        });
    }
}