package com.example.cst338_project2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst338_project2.data.User;
import com.example.cst338_project2.data.Item;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Item.class}, version= 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "MY_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String ITEM_TABLE = "ITEM_TABLE";

    public abstract MyDao getMyDao();

    // https://developer.android.com/codelabs/android-room-with-a-view#7
//    private static AppDatabase instance;
    private static MyDao instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyDao getDatabase(final Context context) {

                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "DB_NAME")
                            .allowMainThreadQueries()
                            .build()
                            .getMyDao();
                }

        return instance;
    }

}
