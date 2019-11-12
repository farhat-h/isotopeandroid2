package com.alttab.isotopeandroid.Tasks;

public interface SessionLoaderCallbacks<T> {

    public void onResults(T objects);
    public void onTaskDone(T objects);

}
