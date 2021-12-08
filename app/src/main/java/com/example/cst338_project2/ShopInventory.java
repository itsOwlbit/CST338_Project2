package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.Item;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import java.util.List;

public class ShopInventory extends AppCompatActivity implements IItemRecyclerView {
    private static final String USER_STATUS_KEY = "com.example.cst338_project2.userStatusKey";
    private static final String ITEM_VIEW_MODE_KEY = "com.example.cst338_project2.itemViewModeKey";
    private static final String ITEM_KEY = "com.example.cst338_project2.itemKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    private SharedPreferences preferences = null;
    int userAccess;

    private MyDao myDao;
    private List<Item> itemList;

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    ItemAdapter itemAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_inventory);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Get list of items
        itemList = myDao.getAllItems();

        prepareLayout();

        determineUserAccess();

        buildRecyclerView();

        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.iron));
        toolbarTitleField.setText("Shop's Inventory");
        logoutField = findViewById(R.id.logoutText);
        logoutField.setVisibility(View.INVISIBLE);
    }

    private void determineUserAccess() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        userAccess = preferences.getInt(USER_STATUS_KEY, -1);
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
                ShopInventory.super.onBackPressed();
            }
        });

    }

    @Override
    public void onButtonClick(int position) {
        int id = itemList.get(position).getItemId();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ITEM_VIEW_MODE_KEY, 3);
        editor.putInt(ITEM_KEY, id);
        editor.commit();
        Intent intent = new Intent(ShopInventory.this, ItemDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Need to update the adapter with an updated list of items from the database.
        itemAdapter.updateData(myDao.getAllItems());
        // Need to update itemList so the array size gets updated.
        itemList = myDao.getAllItems();
        // Do the update on display.
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}