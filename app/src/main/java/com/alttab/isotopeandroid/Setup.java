package com.alttab.isotopeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alttab.isotopeandroid.Tasks.SuccessFailCallbacks;
import com.alttab.isotopeandroid.Workers.VersionCheckWorker;
import com.alttab.isotopeandroid.utils.Util;

import java.util.concurrent.TimeUnit;

public class Setup extends AppCompatActivity implements SuccessFailCallbacks {

    Util tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        tools = Util.getInstance(this);
        setupVersionService();
        tools.updateDatabase(this);
    }

    private void setupVersionService() {
        Constraints constraints
                = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //@TODO REPLACE WORKER
        PeriodicWorkRequest wr = new PeriodicWorkRequest
                .Builder(VersionCheckWorker.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("UPDATE_WORK_REQUEST", ExistingPeriodicWorkPolicy.KEEP, wr);

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onPostExecute(boolean updated) {
        boolean shouldNotPass = !updated && (tools.preferenceManager.dbversion().isEmpty());
        if (shouldNotPass) {
            Toast.makeText(this, "Unable to get download database,\n" +
                    " try again later", Toast.LENGTH_LONG).show();
            // @TODO SET UP ERROR SCREEN
        } else {
            if (tools.preferenceManager.majorId().isEmpty()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (tools.preferenceManager.subgroup() == -1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(MainActivity.K_J2SS, true);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ScheduleActivity.class);
                startActivity(intent);
            }
            finish();
        }

    }
}
