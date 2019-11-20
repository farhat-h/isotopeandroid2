package com.alttab.isotopeandroid.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class NamedSession {

    public static final String EMPTY_ID = "EMPTY_ID";
    public static final String TYPE_TP = "TP";
    public static final String TYPE_TD = "TD";
    public static final String TYPE_C = "C";
    @NonNull
    @PrimaryKey
    public final String sessionId;

    public final String time, subject, professor, type, room, regime;

    public final String majorId;

    public final int day, subGroup;
    public final String fullName;

    public NamedSession(@NonNull String sessionId, int day, String time, String subject, String professor, String type, String room, String regime, int subGroup, String majorId, String fullName) {
        this.sessionId = sessionId;
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.professor = professor;
        this.type = type;
        this.room = room;
        this.regime = regime;
        this.subGroup = subGroup;
        this.majorId = majorId;
        this.fullName = fullName;
    }
}
