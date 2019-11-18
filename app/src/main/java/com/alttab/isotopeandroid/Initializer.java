package com.alttab.isotopeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;

public class Initializer extends AppCompatActivity {

    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initializer);
        helper = Helper.getInstance(this);
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);
        Intent selectIntent = new Intent(this, MainActivity.class);
        WorkManager.getInstance(this).enqueueUniquePeriodicWork()
        if (helper.getIsInitialized())
            startActivity(scheduleIntent);
        else
            startActivity(selectIntent);


    }
}
