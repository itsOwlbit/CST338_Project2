package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShopperHome extends AppCompatActivity {

    String name;
    String welcomeMessage;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_home);

        name = getIntent().getStringExtra("username");

        welcome = findViewById(R.id.welcomeText);

        welcomeMessage = "Welcome " + name;
        welcome.setText(welcomeMessage);



//        welcomeText.setText(getIntent().getStringExtra(username));


    }
}