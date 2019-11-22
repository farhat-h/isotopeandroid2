package com.alttab.isotopeandroid.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegimeDAO {

    @Query("select * from regimes where :day between startDay and endDay AND :month between startMonth and endMonth")
    public Regime currentRegime(int day, int month);
}
