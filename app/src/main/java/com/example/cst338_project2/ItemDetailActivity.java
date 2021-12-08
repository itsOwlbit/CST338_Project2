package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.Item;
import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import org.w3c.dom.Text;

import java.util.InputMismatchException;

public class ItemDetailActivity extends AppCompatActivity {
    private static final String USER_STATUS_KEY = "com.example.cst338_project2.userStatusKey";
    private static final String ITEM_VIEW_MODE_KEY = "com.example.cst338_project2.itemViewModeKey";
    private static final String ITEM_KEY = "com.example.cst338_project2.itemKey";
    private static final String PREFERENCES_KEY = "com.example.cst338_project2.preferencesKey";

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    private MyDao myDao;

    TextView itemNameField;
    TextView itemDescField;
    TextView qtyInStockField;
    TextView itemPriceField;
    TextView itemUnitField;
    TextView deleteLink;

    EditText qtyInStockInput;
    EditText itemPriceInput;
    EditText itemUnitInput;

    Button leftBtn;
    Button rightBtn;

    int qtyInStock;
    int itemPrice;

    String itemName;
    String itemDescription;
    String itemUnit;

    private SharedPreferences preferences = null;

    int modeKey;
    int itemId = -1;

    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        findKeys();

        prepareLayout();

        if(itemId != -1) {
            item = myDao.getItemById(itemId);
            resetFields();
        }

        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.aqua));
        logoutField = findViewById(R.id.logoutText);
        logoutField.setVisibility(View.INVISIBLE);

        // Text
        itemNameField = findViewById(R.id.itemNameText);
        itemDescField = findViewById(R.id.itemDescriptionText);
        qtyInStockField = findViewById(R.id.qtyInStockText);
        itemPriceField = findViewById(R.id.itemPriceText);
        itemUnitField = findViewById(R.id.itemUnitText);
        deleteLink = findViewById(R.id.deleteItemText);
        itemUnitInput = findViewById(R.id.itemUnitValue);

        // Values
        qtyInStockInput = findViewById(R.id.qtyInStockValue);
        itemPriceInput = findViewById(R.id.itemPriceValue);

        // Set Buttons
        leftBtn = findViewById(R.id.leftItemButton);
        rightBtn = findViewById(R.id.rightItemButton);

        // Determine button text by mode
        switch (modeKey) {
            case 1:
                // Admin Add Item Mode
                leftBtn.setText("Clear");
                rightBtn.setText("Add");
                deleteLink.setVisibility(View.INVISIBLE);
                toolbarTitleField.setText("Add Item");
                break;
            case 2:
                // Admin Edit Item Mode
                leftBtn.setText("Reset");
                rightBtn.setText("Edit");
                toolbarTitleField.setText("Edit Item");
                break;
            case 3:
                // User Buy Item Mode
                leftBtn.setText("Back");
                rightBtn.setText("Buy");
                toolbarTitleField.setText("Item Details");
                break;
        }
    }

    private void findKeys() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        modeKey = preferences.getInt(ITEM_VIEW_MODE_KEY, -1);

        // Admin level for editting item
        if(modeKey == 2) {
            itemId = preferences.getInt(ITEM_KEY, -1);

            // Failed to get itemId so put screen at add item instead of edit.
            if(itemId == -1) {
                Toast.makeText(this, "Failed to retrieve item id.", Toast.LENGTH_SHORT).show();
                modeKey = 1;
            }
        }
    }

    private void checkListeners() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetailActivity.super.onBackPressed();
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(modeKey) {
                    case 1:
                        clearFields();
                        break;
                    case 2:
                        resetFields();
                        break;
                    case 3:
                        backToInventoryView();
                        break;
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(modeKey) {
                    case 1:
                        if(addItemToDB()) {
                            Toast.makeText(ItemDetailActivity.this, itemName + " added to Inventory.", Toast.LENGTH_SHORT).show();
                            clearFields();
                        }
                        break;
                    case 2:
                        editItemInDB();
                        break;
                    case 3:
                        buyItem();
                        break;
                }
            }
        });
    }

    private void clearFields() {
        itemNameField.setText("");
        itemDescField.setText("");
        qtyInStockInput.setText("");
        itemPriceInput.setText("");
        itemUnitInput.setText("");
    }

    private boolean addItemToDB() {
        itemName = itemNameField.getText().toString();
        itemDescription = itemDescField.getText().toString();
        itemUnit = itemUnitInput.getText().toString();
        
        if(checkForValidRecord()) {
            if(checkForItemInDatabase()) {
                Toast.makeText(this, "Duplicate item names not allowed.  Try again.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                item = new Item(itemName, itemDescription, qtyInStock, itemPrice, itemUnit, "", 1);
                myDao.insert(item);
                return (checkForItemInDatabase());
            }
        } else {
            return false;
        }
    }

    private void resetFields() {
        itemNameField.setText(item.getItemName());
        itemDescField.setText(item.getItemDescription());
        qtyInStockInput.setText(String.valueOf(item.getInStockQty()));
        itemPriceInput.setText(String.valueOf(item.getItemPrice()));
        itemUnitInput.setText(item.getItemUnit());
    }

    private void editItemInDB() {
        itemName = itemNameField.getText().toString();
        itemDescription = itemDescField.getText().toString();
        itemUnit = itemUnitInput.getText().toString();

        // check for duplicate names for changed name. TODO: Stop name duplication?

        if(itemName != item.getItemName()) {
            Item tempItem = myDao.getItemByName(itemName);
            if (tempItem != null) {
                Toast.makeText(this, "Name ERROR: creates duplicate name.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

//        if(itemName != item.getItemName()) {
//            Item tempItem = myDao.getItemByName(itemName);
//            if(tempItem != null) {
//                Toast.makeText(this, "Name ERROR: creates duplicate name.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

        if(checkForValidRecord()) {
            item.setItemName(itemName);
            item.setItemDescription(itemDescription);
            item.setItemUnit(itemUnit);
            item.setInStockQty(qtyInStock);
            item.setItemPrice(itemPrice);
            myDao.update(item);
            Toast.makeText(this, item.getItemName() + " has been updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    private void backToInventoryView() {
        Toast.makeText(this, "Back to inventory view coming soon.", Toast.LENGTH_SHORT).show();
    }

    private void buyItem() {
        Toast.makeText(this, "Buy items coming soon.", Toast.LENGTH_SHORT).show();
    }
    
    private boolean checkForValidRecord() {
        if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemDescription)
                || TextUtils.isEmpty(itemUnit)) {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // Check for valid Quantity input
            try {
                qtyInStock = Integer.parseInt(qtyInStockInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter Units In Stock.", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Check for valid Price input
            try {
                itemPrice = Integer.parseInt(itemPriceInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter Per Unit Price", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Quantity must be 0 or more
            if (qtyInStock < 0) {
                qtyInStockInput.setText("");
                Toast.makeText(this, "Cannot have negative units in stock.", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Price must be 1 or more
            if (itemPrice <= 0) {
                itemPriceInput.setText("");
                Toast.makeText(this, "Item must cost 1 diamond or more.", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }
    }

    private boolean checkForItemInDatabase() {
        item = myDao.getItemByName(itemName);
        return (item != null);
    }
}