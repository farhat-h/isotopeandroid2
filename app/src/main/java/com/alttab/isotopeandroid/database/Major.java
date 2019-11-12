package com.alttab.isotopeandroid.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Majors")
public class Major {

    @NonNull
    @PrimaryKey
    @ColumnInfo(index = true)
    public final String majorId;
    public final String year, group, fullName, majorName;

    public Major(String majorId, String year, String group, String fullName, String majorName) {
        this.majorId = majorId;
        this.year = year;
        this.group = group;
        this.fullName = fullName;
        this.majorName = majorName;
    }

    @NonNull
    @Override
    public String toString() {
        return fullName;
    }
}
