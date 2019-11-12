package com.alttab.isotopeandroid.ViewHolders;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.Constants;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Session;

import java.util.HashMap;

public class SessionViewHolder extends RecyclerView.ViewHolder {

    private static HashMap<String, String> timeEnds;
    private static HashMap<String, String> timeStarts;


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
    }

    public void setmSession(Session mSession) {
        setupSession(this.itemView, mSession);
    }

    private void setupSession(View view, Session session) {

        TextView subject = view.findViewById(R.id.Subject);
        TextView type = view.findViewById(R.id.SessionType);
        TextView room = view.findViewById(R.id.SessionRoom);
        TextView regime = view.findViewById(R.id.SessionRegime);
        TextView professor = view.findViewById(R.id.SessionProfessor);

        TextView time = view.findViewById(R.id.SessionTimeNumber);
        TextView timeStart = view.findViewById(R.id.SessionTimeStart);
        TextView timeEnd = view.findViewById(R.id.SessionTimeEnd);

        time.setText(session.time);
        timeStart.setText(getTimeStarts().get(session.time));
        timeEnd.setText(getTimeEnds().get(session.time));
        subject.setText(extractSubjectName(session.subject));
        type.setText(session.type);
        room.setText(session.room);
        regime.setText(session.regime);
        professor.setText(session.professor);


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
