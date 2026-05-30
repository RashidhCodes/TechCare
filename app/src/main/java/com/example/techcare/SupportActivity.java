package com.example.techcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {

    TextView btnBack, answer1, answer2, answer3;
    TextView arrow1, arrow2, arrow3;
    LinearLayout faq1, faq2, faq3;
    Button btnChat, btnCall;

    // Track open/closed state of each FAQ
    boolean faq1Open = false;
    boolean faq2Open = false;
    boolean faq3Open = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        // Link views
        btnBack = findViewById(R.id.btnBack);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        arrow1  = findViewById(R.id.arrow1);
        arrow2  = findViewById(R.id.arrow2);
        arrow3  = findViewById(R.id.arrow3);
        faq1    = findViewById(R.id.faq1);
        faq2    = findViewById(R.id.faq2);
        faq3    = findViewById(R.id.faq3);
        btnChat = findViewById(R.id.btnChat);
        btnCall = findViewById(R.id.btnCall);

        // BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // FAQ 1 toggle
        faq1.setOnClickListener(v -> {
            if (faq1Open) {
                answer1.setVisibility(View.GONE);
                arrow1.setText("▾");
                faq1Open = false;
            } else {
                answer1.setVisibility(View.VISIBLE);
                arrow1.setText("▴");
                faq1Open = true;
            }
        });

        // FAQ 2 toggle
        faq2.setOnClickListener(v -> {
            if (faq2Open) {
                answer2.setVisibility(View.GONE);
                arrow2.setText("▾");
                faq2Open = false;
            } else {
                answer2.setVisibility(View.VISIBLE);
                arrow2.setText("▴");
                faq2Open = true;
            }
        });

        // FAQ 3 toggle
        faq3.setOnClickListener(v -> {
            if (faq3Open) {
                answer3.setVisibility(View.GONE);
                arrow3.setText("▾");
                faq3Open = false;
            } else {
                answer3.setVisibility(View.VISIBLE);
                arrow3.setText("▴");
                faq3Open = true;
            }
        });

        // CHAT BUTTON
        btnChat.setOnClickListener(v ->
                Toast.makeText(this,
                        "Connecting to live chat...",
                        Toast.LENGTH_SHORT).show()
        );

        // CALL BUTTON — opens phone dialer
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+1800123456"));
            startActivity(callIntent);
        });
    }
}