package com.alttab.isotopeandroid.Tasks;

import android.os.AsyncTask;

public class GeneralAsyncTask extends AsyncTask<Void, Void, Void> {

    private GeneralAsyncTaskCallbacks callbacks;

    public GeneralAsyncTask(GeneralAsyncTaskCallbacks cbs) {
        callbacks = cbs;
    }

    @Override
    protected void onPreExecute() {
        callbacks.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callbacks.onPostExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        callbacks.doInBackground();
        return null;
    }
}
