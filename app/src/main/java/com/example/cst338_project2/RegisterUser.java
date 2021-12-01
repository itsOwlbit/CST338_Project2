package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {

    TextView username; // the TextView for username to be entered.
    TextView password; // the TextView for password to be entered.
    Button signupBtn; // the Button to click to sign in
    TextView signinLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username = findViewById(R.id.usernameValue);
        password = findViewById(R.id.passwordValue);
        signupBtn = findViewById(R.id.signupButton);
        signinLink = findViewById(R.id.signinLink);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterUser.this, "successful", Toast.LENGTH_SHORT).show();
            }
        });

        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = MainActivity.newIntent(RegisterUser.this);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

//    public static Intent newIntent(Context context) {
//        Intent intent = new Intent(context, RegisterUser.class);
//        return intent;
//    }
}