package com.alttab.isotopeandroid.Tasks;

import android.os.AsyncTask;

import com.alttab.isotopeandroid.database.Repository;

import java.lang.ref.WeakReference;

public class CustomSessionLoader extends AsyncTask<Void, Void, Void> {

    private WeakReference<Repository> wrRepo;
    private String majorId;
    private int day;
    private Object objects;
    private SessionLoaderCallbacks callbacks;

    public CustomSessionLoader(int day, String majorId, Repository repository, SessionLoaderCallbacks callbacks) {
        this.day = day;
        this.majorId = majorId;
        this.wrRepo = new WeakReference<>(repository);
        this.callbacks = callbacks;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.objects = wrRepo.get().getSessionsForDay(majorId, day);
        callbacks.onResults(objects);
        return null;
    }


    

    @Override
    protected void onPostExecute(Void aVoid) {
        callbacks.onTaskDone(objects);
    }
}
