package com.alttab.isotopeandroid.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.NamedSession;
import com.alttab.isotopeandroid.database.Session;

public class AlternativeViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private TextView subject;
    private TextView type;
    private TextView room;
    private TextView regime;
    private TextView professor;
    private TextView time;
    private TextView majorFullName;

    public AlternativeViewHolder(@NonNull View itemView) {
        super(itemView);

        subject = itemView.findViewById(R.id.Subject);

        type = itemView.findViewById(R.id.SessionType);

        room = itemView.findViewById(R.id.SessionRoom);

        regime = itemView.findViewById(R.id.SessionRegime);

        professor = itemView.findViewById(R.id.SessionProfessor);

        time = itemView.findViewById(R.id.SessionTimeNumber);

        majorFullName = itemView.findViewById(R.id.MajorFullName);
    }

    public void bind(NamedSession mSession) {
        if (!mSession.sessionId.equals(Session.EMPTY_ID)) {
            subject.setText(extractSubjectName(mSession.subject));
            type.setText(mSession.type);
            room.setText(mSession.room);
            regime.setText(mSession.regime);
            professor.setText(mSession.professor);
            majorFullName.setText(mSession.fullName);
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

}
