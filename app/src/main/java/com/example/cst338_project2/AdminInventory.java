package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.Item;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import java.util.List;

public class AdminInventory extends AppCompatActivity implements IItemRecyclerView {
    private static final String USER_STATUS_KEY = "com.example.cst338_project2.userStatusKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    
    private Button addItemsBtn;

    private MyDao myDao;

    private List<Item> itemList;

    private SharedPreferences preferences = null;

    int userAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Get list of items
        itemList = myDao.getAllItems();

        prepareLayout();

        determineUserAccess();
        
        buildRecyclerView();

        // Check for setOnClickListener() events
        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.aqua));
        toolbarTitleField.setText("Item Inventory");
        logoutField = findViewById(R.id.logoutText);
        logoutField.setVisibility(View.INVISIBLE);
        
        addItemsBtn = findViewById(R.id.itemAddButton);
    }

    private void determineUserAccess() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        userAccess = preferences.getInt(USER_STATUS_KEY, -1);
        Toast.makeText(this, "Status: " + userAccess, Toast.LENGTH_SHORT).show();
    }
    
    private void buildRecyclerView() {
        itemAdapter = new ItemAdapter(itemList,this, this, userAccess);
        recyclerView = findViewById(R.id.rvItem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }

    private void checkListeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminInventory.super.onBackPressed();
            }
        });

        // Add Item click
        addItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminInventory.this, "Add coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onButtonClick(int position) {
        Toast.makeText(this, "Coming soon! " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Need to update the adapter with an updated list of items from the database.
        itemAdapter.updateData(myDao.getAllItems());
        // Do the update on display.
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}