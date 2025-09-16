package com.example.rainbowflashlight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 2000; // Splash screen duration in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Show splash screen and then check login status
        new Handler().postDelayed(() -> {
            // Check login state using shared preferences
            SharedPreferences preferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

            Intent intent;
            if (isLoggedIn) {
                // User is logged in, redirect to MainActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                // User not logged in, redirect to LoginActivity
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish(); // End SplashActivity
        }, SPLASH_DURATION);
    }
}
