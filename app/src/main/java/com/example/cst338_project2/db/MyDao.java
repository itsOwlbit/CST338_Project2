package com.example.cst338_project2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338_project2.data.Users;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void insert(Users... users);

    @Update
    void update(Users... users);

    @Delete
    void delete(Users... users);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY userName ASC")
    LiveData<List<Users>> getUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID = :name")
    Users getUserByUsername(int name);

}
