package com.example.cst338_project2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_project2.db.AppDatabase;

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String itemName;
    private String itemDescription;
    private int inStockQty;
    private int itemPrice;
    private String itemUnit;
    private String itemImg;
    private int isForSale;

    public Item(String itemName, String itemDescription, int inStockQty, int itemPrice,
                         String itemUnit, String itemImg, int isForSale) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.inStockQty = inStockQty;
        this.itemPrice = itemPrice;
        this.itemUnit = itemUnit;
        this.itemImg = itemImg;
        this.isForSale = isForSale;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getInStockQty() {
        return inStockQty;
    }

    public void setInStockQty(int inStockQty) {
        this.inStockQty = inStockQty;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public int getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(int isForSale) {
        this.isForSale = isForSale;
    }
}
