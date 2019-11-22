package com.alttab.isotopeandroid.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;

@Database(entities = {Major.class, Session.class, Regime.class}, version = 1, exportSchema = false)


abstract class Data extends RoomDatabase {
    public abstract MajorsDAO majors();

    public abstract SessionsDAO sessions();

    public abstract RegimeDAO regimes();

    public static Data INSTANCE;

    public static Data getInstance(Application application) {
        File databaseFile = new File(application.getFilesDir(), "database.sqlite");
        synchronized (Data.class) {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(application, Data.class, "DATABASE")
                        .fallbackToDestructiveMigration()
                        .createFromFile(databaseFile)
                        .build();

            return INSTANCE;
        }
    }
}