package com.alttab.isotopeandroid.ViewHolders;

import android.content.Context;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.Constants;
import com.alttab.isotopeandroid.Fragments.AlternativesSheetFragment;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Session;

import java.util.HashMap;


public class SessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

    private Session mSession;

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

    private Context mContext;

    public SessionViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);

        this.mContext = mContext;

        subject = itemView.findViewById(R.id.Subject);

        type = itemView.findViewById(R.id.SessionType);

        room = itemView.findViewById(R.id.SessionRoom);

        regime = itemView.findViewById(R.id.SessionRegime);

        professor = itemView.findViewById(R.id.SessionProfessor);

        time = itemView.findViewById(R.id.SessionTimeNumber);

        timeStart = itemView.findViewById(R.id.SessionTimeStart);

        timeEnd = itemView.findViewById(R.id.SessionTimeEnd);

        itemView.setOnClickListener(this);
    }

    public void setmSession(Session mSession) {

        this.mSession = mSession;
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

    private static String extractSubjectName(String subject) {
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

    @Override
    public void onClick(View v) {
        AlternativesSheetFragment fragment = new AlternativesSheetFragment(mSession.day, mSession.time, mContext);
        FragmentActivity act = (FragmentActivity) mContext;
        fragment.show(act.getSupportFragmentManager(), "ALTERNATIVES");
    }
}
