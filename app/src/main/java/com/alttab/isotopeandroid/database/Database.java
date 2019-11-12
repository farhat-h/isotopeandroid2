package com.alttab.isotopeandroid.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Major.class, Session.class}, version = 1, exportSchema = false)


abstract class Data extends RoomDatabase {
    public abstract MajorsDAO majors();

    public abstract SessionsDAO sessions();

    public static Data INSTANCE;


    public static Data getInstance(Application application) {
        synchronized (Data.class) {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(application, Data.class, "DATABASE")
//                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .createFromAsset("11-11-2019.sqlite")
                        .build();

            return INSTANCE;
        }
    }
}