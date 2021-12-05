package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminInventory extends AppCompatActivity {

    private Button viewItemsBtn;
    private Button addItemsBtn;

    FragmentManager fragmentManager = getSupportFragmentManager();

    public AdminInventory() {
        super(R.layout.fragment_inventory_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.inventoryFrameLayout, InventoryView_Fragment.class, null)
                    .commit();

            viewItemsBtn = findViewById(R.id.itemViewButton);
            addItemsBtn = findViewById(R.id.itemAddButton);

            viewItemsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.inventoryFrameLayout, InventoryView_Fragment.class, null)
                            .commit();
                }
            });

            addItemsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentManager.beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.inventoryFrameLayout, InventoryItem_Fragment.class, null)
                            .commit();
                }
            });


        }
    }
}