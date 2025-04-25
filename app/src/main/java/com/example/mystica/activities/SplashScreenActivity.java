package com.example.mystica.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystica.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 saniye

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_splash_screen); // Aşağıdaki XML'in adı bu olmalı

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish(); // SplashScreen'den geri dönülmesin
        }, SPLASH_DURATION);
    }
}
