package com.alttab.isotopeandroid.Tasks;

import com.alttab.isotopeandroid.database.Repository;

import java.lang.ref.WeakReference;

public interface AdaptiveLoaderCallbacks<T> {
    public void onExecute(WeakReference<Repository> wrRepo);

    public void onPostExecute();
}
