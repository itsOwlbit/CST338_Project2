package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import java.util.List;

public class ShopperHome extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    TextView welcomeField;          // the TextView where the welcome user message is displayed
    TextView deleteAccountLink;     // the TextView clickable link to delete user's account

    Button shopBtn;     // the button used to view shop inventory screen
    Button orderBtn;    // the button used to view orders screen
    Button logoutBtn;   // the button used to logout

    private MyDao myDao;    // stores singleton database object

    private int userId;     // a userId received from intent extra
    private User user;      // a User object of user logged in

    String welcomeMessage;  // test String to display welcome message in TextView welcome

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_home);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Get userId's object from database
        retrieveUserFromDatabase(); // TODO: Is it possible for this to fail?  null user?

        // Initialize widgets and elements for display layout
        prepareLayout();

        // TODO: Should I put this in prepareLayout()?
        welcomeMessage = "Welcome " + user.getUserName();
        welcomeField.setText(welcomeMessage);

        // Check for setOnClickListener() events
        checkListeners();
    }

    private void retrieveUserFromDatabase() {
        // Locate user's database entry in User table
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
        user = myDao.getUserByUserId(userId);
    }

    private void prepareLayout() {
        welcomeField = findViewById(R.id.welcomeText);
        shopBtn = findViewById(R.id.shopButton);
        orderBtn = findViewById(R.id.ordersButton);
        logoutBtn = findViewById(R.id.logoutButton);
        deleteAccountLink = findViewById(R.id.deleteAccountLink);
    }

    private void checkListeners() {
        // ClickListener for shop button
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopperHome.this, "Shop screen not set up yet.", Toast.LENGTH_SHORT).show();
            }
        });

        // ClickListener for order button
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopperHome.this, "Order screen not set up yet.", Toast.LENGTH_SHORT).show();
            }
        });

        // ClickListener for logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // ClickListener for delete account link
        deleteAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Maybe only delete accounts that have no orders but disable those that do.
                // TODO: What about deleting admin accounts?
                confirmDeleteAccount();
            }
        });
    }

    private void logoutUser() {
        // TODO: Create custom alert dialog for logout
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.logoutQuestion);

        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // clear user from intent
                getIntent().putExtra(USER_ID_KEY, -1);

                // clear user preference
                preferences = null;

                // go to MainActivity (login screen)
                Intent intent = new Intent(ShopperHome.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertBuilder.create().show();
    }

    private void confirmDeleteAccount() {
        // TODO: Create custom alert dialog for logout
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.confirmDeleteAccount);

        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // clear user from intent
                getIntent().putExtra(USER_ID_KEY, -1);

                // set user isActive to 0, which means deleted/deactivated
                user.setIsActive(0);
                myDao.update(user);

                // clear user preference
                preferences = null;

                // go to MainActivity (login screen)
                Intent intent = new Intent(ShopperHome.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertBuilder.create().show();
    }
}