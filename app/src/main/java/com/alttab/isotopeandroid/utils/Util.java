package com.alttab.isotopeandroid.utils;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.alttab.isotopeandroid.Tasks.SuccessFailCallbacks;
import com.alttab.isotopeandroid.database.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class Util {

    public static Util instance;
    public FileManager fileManager;
    public PreferenceManager preferenceManager;
    public Fetcher fetcher;
    private Context context;
    public Repository repo;

    public static synchronized Util getInstance(Context context) {
        if (instance == null) {
            instance = new Util(context);
        }
        return instance;
    }

    public static synchronized Util getExtendedInstance(Application app) {
        if (instance == null) {
            instance = new Util(app.getApplicationContext());
        } else {
            if (instance.repo == null)
                instance.repo = new Repository(app, instance.fileManager.getDatabaseFile());
        }

        return instance;
    }

    private Util(Context context) {
        fileManager = new FileManager(context);
        preferenceManager = new PreferenceManager(context);
        fetcher = new Fetcher();
        this.context = context;
    }

    private Util(Application application) {
        fileManager = new FileManager(context);
        preferenceManager = new PreferenceManager(context);
        fetcher = new Fetcher();
        repo = new Repository(application, fileManager.getDatabaseFile());
        this.context = application.getApplicationContext();
    }

    public void updateDatabase(SuccessFailCallbacks callbacks) {
        new AsyncNetworkTask(callbacks, context).execute();
    }

    public boolean updateDatabaseSync() {
        try {
            String remoteVersion = fetcher.remoteVersion();
            String localVersion = preferenceManager.dbversion();

            boolean shouldUpdate = (remoteVersion != null && !remoteVersion.isEmpty()) && !remoteVersion.equals(localVersion);
            if (shouldUpdate) {
                InputStream in = fetcher.remoteDatabaseAsStream();
                fileManager.override(in);
                preferenceManager.setDbversion(remoteVersion);
                return true;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class AsyncNetworkTask extends AsyncTask<Void, Void, Void> {
        private boolean isSuccessful = false;
        private SuccessFailCallbacks callbacks;
        private WeakReference<Context> contextWeakReference;

        private AsyncNetworkTask(SuccessFailCallbacks callbacks, Context context) {
            this.callbacks = callbacks;
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            isSuccessful = getInstance(contextWeakReference.get()).updateDatabaseSync();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callbacks.onPostExecute(isSuccessful);
        }
    }
}
