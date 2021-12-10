package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.adapters.OrderAdapter;
import com.example.cst338_project2.data.Item;
import com.example.cst338_project2.data.Order;
import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;
import com.example.cst338_project2.interfaces.IOrderRecyclerView;

import java.util.List;

/**
 * Title: ShopperOrderHistory.java
 * Description: This is where shoppers can view their purchase history and make returns. No
 * real exchange of currency is actually made.  So if they return an item, it is not like
 * they are getting any diamonds back since they actually never paid any either. LOL
 * Design File: activity_shopper_order_history.xml
 * Author: Juli S.
 * Date: 12/8/2021
 */

public class ShopperOrderHistory extends AppCompatActivity implements IOrderRecyclerView {
    private static final String USER_ID_KEY = "com.example.cst338_project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    private SharedPreferences preferences = null;
    int userId;

    private MyDao myDao;
    private List<Order> orderList;

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    OrderAdapter orderAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_order_history);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        prepareLayout();

        accessPreferences();

        // Get list of items
        orderList = myDao.getAllOrdersByUserId(userId);

        buildRecyclerView();

        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.iron));
        toolbarTitleField.setText("Your Order History");
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
                isAdmin, null);
        recyclerView = findViewById(R.id.rvOrder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }

    private void checkListeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopperOrderHistory.super.onBackPressed();
            }
        });

    }

    @Override
    public void onButtonClick(int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.orderReturn);

        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // find order entry in Order db
                int id = orderList.get(position).getOrderId();
                Order returnOrder = myDao.getOrderById(id);

                // find returned item's entry in Item db
                int itemId = returnOrder.getBoughtItemId();
                Item returnedItem = myDao.getItemById(itemId);

                // check if item exists
                if(returnedItem == null) {
                    // Item was discontinued, thus is deleted from Item db
                    // Item is just thrown into the void, cause why not?
                    myDao.delete(returnOrder);
                    Toast.makeText(ShopperOrderHistory.this, "Return successful."
                            + "  Discontinued item thrown into the void.", Toast.LENGTH_SHORT).show();
                } else {
                    // Item is in db
                    // Item qty updated and order deleted.  Return completed.
                    returnedItem.setInStockQty(returnedItem.getInStockQty() + 1);
                    myDao.update(returnedItem);
                    myDao.delete(returnOrder);
                    Toast.makeText(ShopperOrderHistory.this, "Return successful.  "
                                    + returnedItem.getItemName() + " is returned to inventory.",
                            Toast.LENGTH_SHORT).show();
                }

                // Need to update the adapter with an updated list of orders from the database.
                orderAdapter.updateData(myDao.getAllOrdersByUserId(userId));
                // update list for activity
                orderList = myDao.getAllOrdersByUserId(userId);
                // Do the update on display.
                recyclerView.getAdapter().notifyDataSetChanged();
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