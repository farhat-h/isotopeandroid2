package com.alttab.isotopeandroid.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alttab.isotopeandroid.Helper;
import com.alttab.isotopeandroid.utils.Util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VersionCheckWorker extends Worker {
    private Util tools;

    public VersionCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        tools = Util.getInstance(context);
    }


    @NonNull
    @Override
    public Result doWork() {
        boolean updateStatus = tools.updateDatabaseSync();
        Log.e("FROM WORKER", updateStatus ? "SUCCESS" : "FAIL");
        return updateStatus ? Result.success() : Result.failure();
    }

}
