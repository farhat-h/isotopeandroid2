package com.alttab.isotopeandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alttab.isotopeandroid.Workers.DownloadDatabaseSync;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class DownloadTask extends AsyncTask<Void, Void, Void> {

    private WeakReference<Context> contextWeakReference;
    private SuccessFailCallbacks callbacks;
    private boolean isSuccessful;

    public void setCallbacks(SuccessFailCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public DownloadTask(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        DownloadDatabaseSync downloader = new DownloadDatabaseSync(contextWeakReference.get());
        try {
            isSuccessful = downloader.download();
            if (isSuccessful)
                callbacks.onSuccess();
            else
                callbacks.onFail();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            callbacks.onFail();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callbacks.onPostExecute(isSuccessful);
    }
}
