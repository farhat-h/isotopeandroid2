package com.alttab.isotopeandroid.database;


import androidx.room.Dao;
import androidx.room.Query;


import java.util.List;

@Dao
public interface SessionsDAO {

    @Query("SELECT * FROM Sessions WHERE day==:day AND time ==:time AND majorId in (SELECT majorId from Majors where majorId <>:majorId AND majorName ==:majorName AND year==:year)")
    public List<Session> getAlternativeSessions(int day, String time, String majorId, String majorName, String year);


    @Query("SELECT * FROM Sessions WHERE day ==:day AND majorId == :majorId AND subGroup==1")
    public List<Session> getDaySessions(String majorId, int day);


    @Query("SELECT * FROM Sessions WHERE day ==:day AND majorId == :majorId and subGroup==2 UNION " +
            "SELECT * from Sessions WHERE day ==:day AND majorId==:majorId and subGroup==1 and time not in (" +
            "SELECT time from Sessions WHERE day==:day AND majorId==:majorId and subGroup==2)")
    public List<Session> getSecondGroupSessions(String majorId, int day);



}


