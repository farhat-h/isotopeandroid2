package com.alttab.isotopeandroid.database;

import android.app.Application;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class Repository {

    // THINGS THAT ARE DOABLE TO ANY/ALL DATA
    private MajorsDAO majors;
    private SessionsDAO sessions;
    private int subgroup = 1;
    private RegimeDAO regimes;

    public Repository(Application application) {
        Data database = Data.getInstance(application);
        majors = database.majors();
        sessions = database.sessions();
//        Calendar calendar = Calendar.getInstance();
        regimes = database.regimes();
    }

    public void setSubgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public List<Major> getAllMajors() {
        return majors.getAllMajors();
    }

    public List<Session> getSessionsForDay(String majorId, int day) {

        if (subgroup == 1)
            return sessions.getDaySessions(majorId, day);
        else
            return sessions.getSecondGroupSessions(majorId, day);
    }

    public List<NamedSession> getAlternativeSessions(int day, String majorId, String time, String majorName, String year) {
        return sessions.getAlternativeSessions(day, time, majorId, majorName, year);
    }

    public Regime getCurrentRegime(int day, int month) {
        return regimes.currentRegime(day, month);
    }

    public Major getMajorById(String majorId) {
        return majors.getMajorById(majorId);
    }

}

