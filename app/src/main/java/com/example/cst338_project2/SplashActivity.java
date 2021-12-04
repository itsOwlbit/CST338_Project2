package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created (SplashActivity.java and activity_splash.xml) with the help of:
 * YouTube creator: Easy Tuto
 * YouTube title: How to make Splash Screen (Loading Screen) | Android Studio | Beginners Tutorial
 * YouTube date: January 5, 2020
 * YouTube link: https://www.youtube.com/watch?v=WyAzD7RMwHM
 */

public class SplashActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    private SharedPreferences preferences = null;

    private int userId = -1;        // default userId if a valid userId is not received

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }, 2000);

    }
}