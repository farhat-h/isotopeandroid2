package com.alttab.isotopeandroid.Tasks;

import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.alttab.isotopeandroid.database.Repository;

import java.lang.ref.WeakReference;

public class AdaptiveTaskLoad extends AsyncTask<Void, Void, Void> {
    private WeakReference<Repository> wrRepo;
    private AdaptiveLoaderCallbacks callbacks;

    public AdaptiveTaskLoad(Repository repository, @Nullable AdaptiveLoaderCallbacks callbacks) {
        this.wrRepo = new WeakReference<>(repository);
        this.callbacks = callbacks;
    }

    public void setCallbacks(AdaptiveLoaderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        callbacks.onExecute(wrRepo);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callbacks.onPostExecute();
    }
}

