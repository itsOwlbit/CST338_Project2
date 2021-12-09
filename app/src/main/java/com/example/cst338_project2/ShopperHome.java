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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

/**
 * Title: ShopperHome.java
 * Description: The home screen for shopper users.  Provides navigation to various areas a
 * shopper has access to.  The logout clears user shared preferences.  The delete account
 * link deletes (actually it is more deactivate right now) the user's account and sends them
 * back to the login screen.  Shared preferences are also cleared.
 * Design File: activity_shopper_home.xml
 * Author: Juli S.
 * Date: 11/28/2021
 */

public class ShopperHome extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    ImageView backImg;      // the image from toolbar_layout to go back
    TextView toolbarTitleField;
    TextView logoutField;   // the text from toolbar_layout to logout

    TextView welcomeField;          // the TextView where the welcome user message is displayed
    TextView deleteAccountLink;     // the TextView clickable link to delete user's account

    Button shopBtn;     // the button used to view shop inventory screen
    Button orderBtn;    // the button used to view orders screen

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

        // Check for user preferences
        setUserPreferences();

        // Get userId's object from database
        retrieveUserFromDatabase();

        // Initialize widgets and elements for display layout
        prepareLayout();

        // Check for setOnClickListener() events
        checkListeners();
    }

    private void setUserPreferences() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        // userId gets overridden by the intent's Extra, but above line is needed for
        // logout and saving preferences.
        userId = preferences.getInt(USER_ID_KEY, -1);
    }

    private void retrieveUserFromDatabase() {
        // Locate user's database entry in User table
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
        user = myDao.getUserByUserId(userId);
    }

    private void prepareLayout() {
        backImg = findViewById(R.id.backImg);
        backImg.setVisibility(View.INVISIBLE); // not needed to be seen on home page
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.iron));
        logoutField = findViewById(R.id.logoutText);

        welcomeField = findViewById(R.id.welcomeText);
        shopBtn = findViewById(R.id.shopButton);
        orderBtn = findViewById(R.id.ordersButton);
        deleteAccountLink = findViewById(R.id.deleteAccountLink);

        welcomeMessage = "Welcome " + user.getUserName();
        welcomeField.setText(welcomeMessage);
    }

    private void checkListeners() {
        // ClickListener for logout Text in toolbar_layout
        logoutField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // ClickListener for shop button
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopperHome.this, ShopInventory.class);
                startActivity(intent);
            }
        });

        // ClickListener for order button
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopperHome.this,
                        ShopperOrderHistory.class);
                startActivity(intent);
            }
        });

        // ClickListener for delete account link
        deleteAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAccount();
            }
        });
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.logoutQuestion);

        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // clear user from intent
                getIntent().putExtra(USER_ID_KEY, -1);

                // clear preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();

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

                // clear preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();

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