package com.example.marketfinder.database;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.marketfinder.dao.MarketDao;
import com.example.marketfinder.dao.UserDao;
import com.example.marketfinder.entities.Market;
import com.example.marketfinder.entities.User;

@Database(entities = {User.class, Market.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "marketfinder.db";

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract MarketDao marketDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
