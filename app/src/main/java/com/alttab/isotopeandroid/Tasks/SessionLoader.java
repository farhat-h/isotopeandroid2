package com.alttab.isotopeandroid.Tasks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.alttab.isotopeandroid.Constants;
import com.alttab.isotopeandroid.database.Repository;
import com.alttab.isotopeandroid.database.Session;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SessionLoader extends AsyncTaskLoader<List<Session>> {

    private WeakReference<Repository> wrRepo;
    private String majorId;
    private int day;
    public static Comparator<Session> sessionComparator = new Comparator<Session>() {
        @Override
        public int compare(Session o1, Session o2) {
            return o1.time.compareTo(o2.time);
        }
    };

    public SessionLoader(@NonNull Context context, Repository repository, String majorId, int day) {
        super(context);
        wrRepo = new WeakReference<>(repository);
        this.majorId = majorId;
        this.day = day;
    }

    @Nullable
    @Override
    public List<Session> loadInBackground() {
        List<Session> sessions = wrRepo.get().getSessionsForDay(majorId, day);

        HashMap<String, Boolean> existingSessions = new HashMap<>();
        for (Session s : sessions) {
            existingSessions.put(s.time, true);
        }

        for (String time : Constants.TIMES) {
            if (!existingSessions.containsKey(time)) {
                sessions.add(Session.createEmpty(time));
            }
        }
        Collections.sort(sessions, sessionComparator);
        return sessions;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
