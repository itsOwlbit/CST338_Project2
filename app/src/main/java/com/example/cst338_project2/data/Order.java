package com.example.cst338_project2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_project2.db.AppDatabase;

@Entity(tableName = AppDatabase.ORDER_TABLE)
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderId;

    private int buyerId;
    private int boughtItemId;
    private int orderPrice;
    private String boughtItemDescription;
    private int isReturned;

    public Order(int buyerId, int boughtItemId, int orderPrice, String boughtItemDescription, int isReturned) {
        this.buyerId = buyerId;
        this.boughtItemId = boughtItemId;
        this.orderPrice = orderPrice;
        this.boughtItemDescription = boughtItemDescription;
        this.isReturned = isReturned;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getBoughtItemId() {
        return boughtItemId;
    }

    public void setBoughtItemId(int boughtItemId) {
        this.boughtItemId = boughtItemId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getBoughtItemDescription() {
        return boughtItemDescription;
    }

    public void setBoughtItemDescription(String boughtItemName) {
        this.boughtItemDescription = boughtItemName;
    }

    public int getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(int isReturned) {
        this.isReturned = isReturned;
    }
}
