package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView username; // the TextView for username to be entered.
    TextView password; // the TextView for password to be entered.
    Button signinBtn; // the Button to click to sign in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameValue);
        password = findViewById(R.id.passwordValue);
        signinBtn = findViewById(R.id.signinButton);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString() != "" && password.getText().toString() != "") {
                    Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}