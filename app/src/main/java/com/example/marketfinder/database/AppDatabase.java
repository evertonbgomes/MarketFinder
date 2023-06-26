package com.example.marketfinder.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "marketfinder.db";

    private static AppDatabase instance;


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
