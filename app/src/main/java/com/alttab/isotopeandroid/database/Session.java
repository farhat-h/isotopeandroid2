package com.alttab.isotopeandroid.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("majorId")},
        tableName = "Sessions",
        foreignKeys = {
                @ForeignKey(
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE,
                        entity = Major.class,
                        parentColumns = "majorId",
                        childColumns = "majorId")}
)
public class Session {

    public static Session createEmptySession(String time, int day) {
        Session s = new Session(EMPTY_ID, day, time, null, null, null, null, null, 0, null);
        return s;
    }

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

    public Session(@NonNull String sessionId, int day, String time, String subject, String professor, String type, String room, String regime, int subGroup, String majorId) {
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
    }
}