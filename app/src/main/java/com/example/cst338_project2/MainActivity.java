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

import com.example.cst338_project2.data.Item;
import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String USER_STATUS_KEY = "com.example.cst338_project2.userStatusKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";
    private static final int MAX_LOGIN_ATTEMPTS = 3; // Used to prevent brute force guessing

    TextView usernameField;      // the TextView for username to be entered.
    TextView passwordField;      // the TextView for password to be entered.

    Button signinBtn;       // the Button to click to sign in
    TextView signupLink;    // the TextView clickable link to register a new account

    private MyDao myDao;    // stores singleton database object

    private int userId = -1;        // default userId if a valid userId is not received
    private User user;              // a User object for person logged/logging in

    private String username;        // used to store username retrieved from TextView field
    private String password;        // used to store password retrieved from TextView field
    private int loginAttempts = 0;  // the number of logins attempted

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
    }

    private void checkPreferences() {
        if(preferences == null) {
            preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        }

        userId = preferences.getInt(USER_ID_KEY, -1);

        if(userId != -1) {
            // send user's to correct access level.  isAdmin = 1 is an admin.
            user = myDao.getUserByUserId(userId);

            Intent intent;
            if(user.getIsAdmin() == 1) {
                intent = new Intent(MainActivity.this, AdminHome.class);
            } else {
                intent = new Intent(MainActivity.this, ShopperHome.class);
            }
            intent.putExtra(USER_ID_KEY, user.getUserID());
            startActivity(intent);
        }

        List<User> users = myDao.getAllUsers();
        if(users.size() <= 0) {
            // create default users if User table is empty.
            // TODO: Update default user/admin
            User defaultUser = new User("testuser1", "testuser1", 0, 1);
            User defaultAdmin = new User("admin2", "admin2", 1, 1);
            myDao.insert(defaultUser);
            myDao.insert(defaultAdmin);
            Item stone = new Item("Stone",
                    "Basic stone for any building project.",
                    5, 1, 5, "", 1);
            Item deepSlate = new Item("Deep Slate",
                    "A very beautiful and dark building material.",
                    5, 1, 2, "", 1);
            Item darkOak = new Item("Dark Oak Logs",
                    "For when plain oak or spurce just won't do.",
                    10, 1, 3, "", 1);
            Item oak = new Item("Oak Logs",
                    "So useful in every build.  Use plain, stripped, or planks.",
                    20, 1, 5, "", 1);
            Item spruce = new Item("Spruce Logs",
                    "Adds accent color.  Goes great with stone, oak, and dark oak.",
                    10, 1, 5, "", 1);
            Item pinkSheep = new Item("Purple Cow Sheep",
                    "Comes with name tag and a lead.  You can't dye a cow to get this color.",
                    0, 1, 1, "", 1);
            myDao.insert(stone);
            myDao.insert(deepSlate);
            myDao.insert(darkOak);
            myDao.insert(oak);
            myDao.insert(spruce);
        }
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
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this,
                            "Both username and password are required.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // verify user in database and that password matches
                    if(checkForUserInDatabase() && validatePassword()) {
                        // If account is deactivated, then user needs to make a new account
                        if(user.getIsActive() == 0) {
                            Toast.makeText(MainActivity.this,
                                    "Account " + user.getUserName() +
                                            " has been deactivated.  Make a new account.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userId = user.getUserID();
                        addUserToPreference(userId);

                        // send user's to correct access level.  isAdmin = 1 is an admin.
                        Intent intent;
                        if(user.getIsAdmin() == 1) {
                            intent = new Intent(v.getContext(), AdminHome.class);
                        } else {
                            intent = new Intent(v.getContext(), ShopperHome.class);
                        }
                        intent.putExtra(USER_ID_KEY, user.getUserID());
                        startActivity(intent);
                    } else {
                        loginAttempts++;
                        if(loginAttempts == MAX_LOGIN_ATTEMPTS) {
                            Toast.makeText(MainActivity.this,
                                    "Maximum attempts used.  No more chances. Good Bye.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            System.exit(0);
                        }
                        Toast.makeText(MainActivity.this,
                                "Invalid username and/or password. Only " +
                                        (MAX_LOGIN_ATTEMPTS - loginAttempts) +
                                        " attempts left.", Toast.LENGTH_SHORT).show();
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
            preferences = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // just a check to clear preferences before adding a new one
        editor.commit();
        editor.putInt(USER_ID_KEY, userId);
        editor.putInt(USER_STATUS_KEY, user.getIsAdmin());
        editor.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Clear TextView fields on back on focus.
        usernameField.setText("");
        passwordField.setText("");
    }
}