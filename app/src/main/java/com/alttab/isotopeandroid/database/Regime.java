package com.alttab.isotopeandroid.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "regimes")
public class Regime {

    @ColumnInfo(index = true)
    @PrimaryKey
    @NonNull
    public final String id;
    public final int startMonth;
    public final int endMonth;
    public final int startDay;
    public final int endDay;

    public final String regimeQAB;
    public final String regimeZ;
    public final String regimeM;

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Q")
                .append(regimeQAB)
                .append(" ")
                .append(regimeZ)
                .append(" ")
                .append(regimeM);
        return builder.toString();
    }

    public Regime(@NonNull String id, int startMonth, int endMonth, int startDay, int endDay, String regimeQAB, String regimeZ, String regimeM) {
        this.id = id;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = startDay;
        this.endDay = endDay;
        this.regimeQAB = regimeQAB;
        this.regimeZ = regimeZ;
        this.regimeM = regimeM;
    }
}
