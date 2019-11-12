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

    private Context context;

    public SessionViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
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

        ConstraintLayout container = view.findViewById(R.id.SessionContainer);
        setupColors(session.type, subject, type, room, regime, professor, container);

    }

    private String extractSubjectName(String subject) {
        subject = subject.replaceAll("(\\(.*\\))", "");
        // Pattern p = Pattern.compile("-(.*?)-");

        //Matcher m = p.matcher(subject);
        //if (m.find())
        //  subject = m.group(1);

        return subject;
    }

    private void setupColors(String type, TextView subjectText, TextView typeView, TextView room, TextView regime, TextView professor, ConstraintLayout container) {

        TypedArray themeData = context.getTheme().obtainStyledAttributes(R.styleable.theme);

        switch (type) {
            case "TP":
                container.setBackgroundResource(R.drawable.tp_session_background);
                subjectText.setTextColor(themeData.getColorStateList(R.styleable.theme_session_tp_subject));
                typeView.setTextColor(themeData.getColorStateList(R.styleable.theme_session_tp_chipBackground));

                room.setTextColor(themeData.getColorStateList(R.styleable.theme_session_tp_chipText));
                room.setBackgroundResource(R.drawable.tp_chip_background);
                room.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_room_tp, 0, 0, 0);

                regime.setTextColor(themeData.getColorStateList(R.styleable.theme_session_tp_chipText));
                regime.setBackgroundResource(R.drawable.tp_chip_background);
                regime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_regime_tp, 0, 0, 0);

                professor.setTextColor(themeData.getColorStateList(R.styleable.theme_session_tp_professorText));
                professor.setBackgroundResource(R.drawable.session_chip_background);

                break;

            case "TD":
                container.setBackgroundResource(R.drawable.td_session_background);

                subjectText.setTextColor(themeData.getColorStateList(R.styleable.theme_session_td_subject));
                typeView.setTextColor(themeData.getColorStateList(R.styleable.theme_session_td_chipBackground));

                room.setTextColor(themeData.getColorStateList(R.styleable.theme_session_td_chipText));
                room.setBackgroundResource(R.drawable.td_chip_background);
                room.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_room_td, 0, 0, 0);

                regime.setTextColor(themeData.getColorStateList(R.styleable.theme_session_td_chipText));
                regime.setBackgroundResource(R.drawable.td_chip_background);
                regime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_regime_td, 0, 0, 0);

                professor.setTextColor(themeData.getColorStateList(R.styleable.theme_session_td_professorText));
                professor.setBackgroundResource(R.drawable.session_chip_background);

                break;

            case "C":
                container.setBackgroundResource(R.drawable.c_session_background);

                subjectText.setTextColor(themeData.getColorStateList(R.styleable.theme_session_c_subject));
                typeView.setTextColor(themeData.getColorStateList(R.styleable.theme_session_c_chipBackground));

                room.setTextColor(themeData.getColorStateList(R.styleable.theme_session_c_chipText));
                room.setBackgroundResource(R.drawable.c_chip_background);
                room.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_room_c, 0, 0, 0);


                regime.setTextColor(themeData.getColorStateList(R.styleable.theme_session_c_chipText));
                regime.setBackgroundResource(R.drawable.c_chip_background);
                regime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_regime_c, 0, 0, 0);


                professor.setTextColor(themeData.getColorStateList(R.styleable.theme_session_c_professorText));
                professor.setBackgroundResource(R.drawable.session_chip_background);

                break;
        }
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
