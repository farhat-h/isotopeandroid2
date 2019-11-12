package com.alttab.isotopeandroid.database;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MajorsDAO {

    @Query("SELECT * FROM Majors")
    public List<Major> getAllMajors();

    @Query("SELECT * FROM Majors WHERE majorId==:id")
    public Major getMajorById(String id);

}