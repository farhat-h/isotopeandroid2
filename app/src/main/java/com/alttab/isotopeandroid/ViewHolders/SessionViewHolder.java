package com.alttab.isotopeandroid.ViewHolders;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.Constants;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Session;

import java.util.HashMap;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class SessionViewHolder extends RecyclerView.ViewHolder {

    private static HashMap<String, String> timeEnds;
    private static HashMap<String, String> timeStarts;

    private TextView subject;
    private TextView type;
    private TextView room;
    private TextView regime;
    private TextView professor;
    private TextView time;
    private TextView timeStart;
    private TextView timeEnd;

    public static HashMap<String, String> getTimeEnds() {
        if (timeEnds == null) {
            timeEnds = new HashMap<>();
            for (String time : Constants.TIMES) {
                timeEnds.put(time, getTimeBound(time, "end"));
            }
        }
        return timeEnds;
    }

    public static HashMap<String, String> getTimeStarts() {
        if (timeStarts == null) {
            timeStarts = new HashMap<>();
            for (String time : Constants.TIMES) {
                timeStarts.put(time, getTimeBound(time, "start"));
            }
        }
        return timeStarts;
    }


    public SessionViewHolder(@NonNull View itemView) {
        super(itemView);


        subject = itemView.findViewById(R.id.Subject);

        type = itemView.findViewById(R.id.SessionType);

        room = itemView.findViewById(R.id.SessionRoom);

        regime = itemView.findViewById(R.id.SessionRegime);

        professor = itemView.findViewById(R.id.SessionProfessor);

        time = itemView.findViewById(R.id.SessionTimeNumber);

        timeStart = itemView.findViewById(R.id.SessionTimeStart);

        timeEnd = itemView.findViewById(R.id.SessionTimeEnd);

    }

    public void setmSession(Session mSession) {

        time.setText(mSession.time);
        timeStart.setText(getTimeStarts().get(mSession.time));
        timeEnd.setText(getTimeEnds().get(mSession.time));
        if (!mSession.sessionId.equals(Session.EMPTY_ID)) {

            subject.setText(extractSubjectName(mSession.subject));
            type.setText(mSession.type);
            room.setText(mSession.room);
            regime.setText(mSession.regime);
            professor.setText(mSession.professor);

        }
    }

    private String extractSubjectName(String subject) {
        subject = subject.replaceAll("(\\(.*\\))", "");
        // Pattern p = Pattern.compile("-(.*?)-");

        //Matcher m = p.matcher(subject);
        //if (m.find())
        //  subject = m.group(1);

        return subject;
    }

    private static String getTimeBound(String time, String bound) {
        String start = "", end = "";
        switch (time) {
            case "S1":
                start = "08:30";
                end = "10:00";
                break;

            case "S2":
                start = "10:10";
                end = "11:40";
                break;

            case "S3":
                start = "11:50";
                end = "13:20";
                break;

            case "S4":
                start = "13:50";
                end = "15:20";
                break;
            case "S5":
                start = "15:30";
                end = "17:00";
                break;
            case "S6":
                start = "17:10";
                end = "18:40";
                break;

        }
        return (bound.equals("start")) ? start : end;
    }
}
