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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    TextView usernameField;      // the TextView for username to be entered.
    TextView passwordField;      // the TextView for password to be entered.

    Button signinBtn;       // the Button to click to sign in
    TextView signupLink;    // the TextView clickable link to register a new account

    private MyDao myDao;    // stores singleton database object

    private List<User> userList;    // a list of User objects
    private int userId = -1;        // default userId if a valid userId is not received
    private User user;              // a User object for person logged/logging in

    private String username;    // used to store username retrieved from TextView field
    private String password;    // used to store password retrieved from TextView field

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Check for user preferences
        checkPreferences();

        // Initialize widgets and elements for display layout
        prepareLayout();

        // Check for setOnClickListener() events
        checkListeners();

        // TODO:  Need to setup 3 allowed tries for login
    }

    private void checkPreferences() {
        if(preferences == null) {
            preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        }

        userId = preferences.getInt(USER_ID_KEY, -1);

        if(userId != -1) {
            return;
        }

        List<User> users = myDao.getAllUsers();
        if(users.size() <= 0) {
            // create default users if User table is empty.
            // TODO: Update default user/admin
            User defaultUser = new User("juli", "123", 0, 1);
            User defaultAdmin = new User("admin2", "admin2", 1, 1);
            myDao.insert(defaultUser);
            myDao.insert(defaultAdmin);
        }

        return;
    }

    private void prepareLayout() {
        setContentView(R.layout.activity_main);
        usernameField = findViewById(R.id.usernameValue);
        passwordField = findViewById(R.id.passwordValue);
        signinBtn = findViewById(R.id.signinButton);
        signupLink = findViewById(R.id.signupLink);
    }

    private void checkListeners() {
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from display
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this,
                            "Both username and password are required.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Also check their isActive status
                    // verify user in database and that password matches
                    if(checkForUserInDatabase() && validatePassword()) {
                        // check if account isActive (if == 0) return.  toast: resgister for new acocunt?  account is deactivated.

                        addUserToPreference(userId); // TODO: Is preferences working?

                        // TODO: Send user to appropriate activity screen depending on isAdmin?

                        Intent intent = new Intent(v.getContext(), ShopperHome.class);
                        intent.putExtra(USER_ID_KEY, user.getUserID());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid username and/or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterUser.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkForUserInDatabase() {
        user = myDao.getUserByUsername(username);

        return (user != null);
    }

    private boolean validatePassword() {
        return user.getPassword().equals(password);
    }

    private void addUserToPreference(int userId) {
        if(preferences == null) {
            preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Clear TextView fields on back on focus.
        usernameField.setText("");
        passwordField.setText("");
    }
}