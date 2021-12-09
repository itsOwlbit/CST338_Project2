package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

/**
 * Title: SplashActivity.java
 * Description: This is the first thing that the users see when they start the application.  It
 * serves no function other than to be pretty.  It shows the shop's name and a status
 * circle that just spins for no reason.  The next screen is MainActivity.java.
 * Design File: activity_splash.xml.
 * Author: Juli S.
 * Date: 11/282021
 *
 * ADDITIONAL NOTES:
 * Created with the help of:
 * YouTube creator: Easy Tuto
 * YouTube title: How to make Splash Screen (Loading Screen) | Android Studio | Beginners Tutorial
 * YouTube date: January 5, 2020
 * YouTube link: https://www.youtube.com/watch?v=WyAzD7RMwHM
 */

public class SplashActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    private SharedPreferences preferences = null;

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