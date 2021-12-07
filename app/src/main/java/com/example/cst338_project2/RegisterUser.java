package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

//import java.util.List;

public class RegisterUser extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String USER_STATUS_KEY = "com.example.cst338_project2.userStatusKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    TextView usernameField;      // the TextView for username to be entered.
    TextView passwordField;      // the TextView for password to be entered.

    Button signupBtn;       // the Button to click to sign in
    TextView signinLink;    // the TextView link to take user back to login screen

    private MyDao myDao;    // stores singleton database object

//    private List<User> userList;    // a list of User objects
    private int userId = -1;        // default userId if a valid userId is not received
    private User user;              // a User object for person logged/logging in

    private String username;    // used to store username retrieved from TextView field
    private String password;    // used to store password retrieved from TextView field

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Initialize widgets and elements for display layout
        prepareLayout();

        // Check for setOnClickListener() events
        checkListeners();
    }

    private void prepareLayout() {
        usernameField = findViewById(R.id.usernameValue);
        passwordField = findViewById(R.id.passwordValue);
        signupBtn = findViewById(R.id.signupButton);
        signinLink = findViewById(R.id.signinLink);
    }

    private void checkListeners() {
        // ClickListener for signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterUser.this,
                            "Both username and password are required.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //  check if user is in the database (duplicate username is not allowed)
                    if(checkForUserInDatabase()) {
                        Toast.makeText(RegisterUser.this, username + " is not available.  Please try another.", Toast.LENGTH_SHORT).show();
                    } else {
                        // add user to database and make sure it worked
                        if(addUserToDB()) {
                            addUserToPreference(userId); // TODO: Update Preferences to work

                            // Can only create new shopper users at register screen
                            Intent intent = new Intent(v.getContext(), ShopperHome.class);
                            intent.putExtra(USER_ID_KEY, user.getUserID()); // Todo: is this correct?
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterUser.this, "Something went wrong.  Could not add " + username, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // ClickListener for signin link
        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkForUserInDatabase() {
        user = myDao.getUserByUsername(username);

        return (user != null);
    }

    private void addUserToPreference(int userId) {
        if(preferences == null) {
            preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.putInt(USER_STATUS_KEY, user.getIsAdmin());
    }

    private boolean addUserToDB() {
        User newUser = new User(username, password, 0, 1);
        myDao.insert(newUser);

        return (checkForUserInDatabase());
    }
}