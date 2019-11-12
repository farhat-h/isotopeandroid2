package com.alttab.isotopeandroid.database;


import androidx.room.Dao;
import androidx.room.Query;


import java.util.List;

@Dao
public interface SessionsDAO {

    @Query("SELECT * FROM Sessions WHERE day==:day AND time ==:time AND majorId in (SELECT majorId from Majors where majorId <>:majorId)")
    public List<Session> getAlternativeSessions(int day, String time, String majorId);


    @Query("SELECT * FROM Sessions WHERE day ==:day AND majorId == :majorId")
    public List<Session> getDaySessions(String majorId, int day);
}