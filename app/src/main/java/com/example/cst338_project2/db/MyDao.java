package com.example.cst338_project2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338_project2.data.Order;
import com.example.cst338_project2.data.User;
import com.example.cst338_project2.data.Item;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userName = :name")
    User getUserByUsername(String name);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID = :id")
    User getUserByUserId(int id);

    @Query("SELECT userName FROM " + AppDatabase.USER_TABLE)
    List<String> allUserNames();

    @Insert
    void insert(Item... Items);

    @Update
    void update(Item... Items);

    @Delete
    void delete(Item... Item);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE)
    List<Item> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE itemName = :name")
    Item getItemByName(String name);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE itemId = :id")
    Item getItemById(int id);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE isForSale = \"1\"")
    List<Item> getAllItemsForSale();

    @Insert
    void insert(Order... orders);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE)
    List<Order> getAllOrders();

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE buyerId = :id")
    List<Order> getAllOrdersByUserId(int id);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE orderId = :id")
    Order getOrderById(int id);
}
