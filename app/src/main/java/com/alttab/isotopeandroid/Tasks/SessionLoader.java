package com.alttab.isotopeandroid.Tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.alttab.isotopeandroid.database.Repository;
import com.alttab.isotopeandroid.database.Session;

import java.lang.ref.WeakReference;
import java.util.List;

public class SessionLoader extends AsyncTaskLoader<List<Session>> {

    private WeakReference<Repository> wrRepo;
    private String majorId;
    private int day;

    public SessionLoader(@NonNull Context context, Repository repository, String majorId, int day) {
        super(context);
        wrRepo = new WeakReference<>(repository);
        this.majorId = majorId;
        this.day = day;
    }

    @Nullable
    @Override
    public List<Session> loadInBackground() {
        return wrRepo.get().getSessionsForDay(majorId, day);
    }

    @Override
    protected void onStartLoading() {
            forceLoad();
    }
}
