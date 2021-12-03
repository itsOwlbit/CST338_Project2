package com.example.cst338_project2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_project2.db.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userID;     // auto-generated user ID.  Must be unique.

    private String userName;    // login username.  Must be unique.
    private String password;    // login password.

    private int isAdmin;    // 0 is user/shopper.  1 is admin.

    private int isActive;   // 0 is deactivated/deleted account.  1 is active account.

    public User(String userName, String password, int isAdmin, int isActive) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
