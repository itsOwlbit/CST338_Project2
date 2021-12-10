package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.adapters.OrderAdapter;
import com.example.cst338_project2.data.Order;
import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;
import com.example.cst338_project2.interfaces.IOrderRecyclerView;

import java.util.List;

/**
 * Title: AdminSalesReport.java
 * Description: TODO: fill this out
 * Design File: activity_admin_sales_report.xml
 * Author: Juli S.
 * Date: 12/09/2021
 */

public class AdminSalesReport extends AppCompatActivity implements IOrderRecyclerView {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    private SharedPreferences preferences = null;
    int userId; // used to get preferences because this is needed to setup adapter

    private MyDao myDao;
    private List<Order> orderList;
    private List<User> userList;    // it is passed to adapter to be able to display buyer name

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    OrderAdapter orderAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sales_report);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        prepareLayout();

        accessPreferences();

        // Get list of items
        orderList = myDao.getAllOrders();
        userList = myDao.getAllUsers();

        buildRecyclerView();

        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.aqua));
        toolbarTitleField.setText("Sales Report");
        logoutField = findViewById(R.id.logoutText);
        logoutField.setVisibility(View.INVISIBLE);
    }

    private void accessPreferences() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        userId = preferences.getInt(USER_ID_KEY, -1);
    }

    private void buildRecyclerView() {
        User user = myDao.getUserByUserId(userId);
        int isAdmin = user.getIsAdmin();

        orderAdapter = new OrderAdapter(orderList, this, this,
                isAdmin, userList);
        recyclerView = findViewById(R.id.rvSalesReport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }

    private void checkListeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSalesReport.super.onBackPressed();
            }
        });
    }

    @Override
    public void onButtonClick(int position) {
        // Button has been set invisible since it is not needed in admin sales report view
        Toast.makeText(this, "Welcome to the void", Toast.LENGTH_SHORT).show();
    }
}