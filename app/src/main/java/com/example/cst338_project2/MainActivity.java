package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

public class MainActivity extends AppCompatActivity {

    TextView username; // the TextView for username to be entered.
    TextView password; // the TextView for password to be entered.
    Button signinBtn; // the Button to click to sign in
    TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameValue);
        password = findViewById(R.id.passwordValue);
        signinBtn = findViewById(R.id.signinButton);
        signupLink = findViewById(R.id.signupLink);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this,
                            "Both username and password are required.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // If username is valid and password is same in db, then go to user home
                    // else, toast  a message 1 less try.


                    Intent intent = new Intent(v.getContext(), ShopperHome.class);
                    intent.putExtra("username", name);
                    startActivity(intent);
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();

//                Intent intent = RegisterUser.newIntent(MainActivity.this);
                Intent intent = new Intent(v.getContext(), RegisterUser.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Clear TextView fields on back on focus.
        username.setText("");
        password.setText("");
    }

    //    public static Intent newIntent(Context context) {
//        Intent intent = new Intent(context, MainActivity.class);
//        return intent;
//    }
}