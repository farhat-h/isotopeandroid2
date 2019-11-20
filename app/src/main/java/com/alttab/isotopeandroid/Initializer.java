package com.alttab.isotopeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alttab.isotopeandroid.Tasks.DownloadTask;
import com.alttab.isotopeandroid.Tasks.SuccessFailCallbacks;
import com.alttab.isotopeandroid.Workers.DownloadDatabaseSync;
import com.alttab.isotopeandroid.Workers.VersionCheckWorker;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Initializer extends AppCompatActivity implements SuccessFailCallbacks {

    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initializer);
        helper = Helper.getInstance(this);

        Constraints constraints
                = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest wr = new PeriodicWorkRequest
                .Builder(VersionCheckWorker.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("UPDATE_WORK_REQUEST", ExistingPeriodicWorkPolicy.KEEP, wr);

        try {
            _initDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);
        Intent selectIntent = new Intent(this, MainActivity.class);
        if (helper.getIsInitialized())
            startActivity(scheduleIntent);
        else
            startActivity(selectIntent);
*/


    }

    public void setUILoading() {
        findViewById(R.id.retryButton).setVisibility(View.GONE);
        findViewById(R.id.init_load_progress).setVisibility(View.VISIBLE);

    }

    void setUIRetry() {
        findViewById(R.id.init_load_progress).setVisibility(View.GONE);
        findViewById(R.id.retryButton).setVisibility(View.VISIBLE);
    }

    private void _initDatabase() throws IOException {
        File databaseFile = new File(getFilesDir(), "database.sqlite");
        if (!databaseFile.exists()) {
            downloadFile();
        } else {
            Intent intent;
            if (helper.getIsInitialized())
                intent = new Intent(this, ScheduleActivity.class);
            else
                intent = new Intent(this, MainActivity.class);

            startActivity(intent);
            finish();
        }

    }

    public void downloadFile() throws IOException {
        DownloadTask task = new DownloadTask(this);
        task.setCallbacks(this);
        task.execute();
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onPostExecute(boolean isSuccessful) {
        if (isSuccessful) {
            Intent intent = new Intent(Initializer.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setUIRetry();
        }
    }

    public void onRetryClicked(View view) throws IOException {
        setUILoading();
        _initDatabase();
    }
}
