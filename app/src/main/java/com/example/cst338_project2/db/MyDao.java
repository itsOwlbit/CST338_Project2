package com.example.cst338_project2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338_project2.data.User;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE )
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userName = :name")
    User getUserByUsername(String name);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID = :id")
    User getUserByUserId(int id);

}
