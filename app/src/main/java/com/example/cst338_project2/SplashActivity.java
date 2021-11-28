package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created (SplashActivity.java and activity_splash.xml) with the help of:
 * YouTube creator: Easy Tuto
 * YouTube title: How to make Splash Screen (Loading Screen) | Android Studio | Beginners Tutorial
 * YouTube date: January 5, 2020
 * YouTube link: https://www.youtube.com/watch?v=WyAzD7RMwHM
 */

public class SplashActivity extends AppCompatActivity {

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