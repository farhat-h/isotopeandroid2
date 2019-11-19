package com.alttab.isotopeandroid.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alttab.isotopeandroid.Helper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VersionCheckWorker extends Worker {
    private final static String versionURL = "http://137.74.192.93/api/version";
    private OkHttpClient client;
    private Helper helper;
    private DownloadDatabaseSync downloader;

    public VersionCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.client = new OkHttpClient();
        helper = Helper.getInstance(context);
        downloader = new DownloadDatabaseSync(context);
    }


    @NonNull
    @Override
    public Result doWork() {
        try {
            boolean shouldUpdateDatabase = shouldUpdate();
            if (shouldUpdateDatabase) {
                if (downloader.download()) {
                    Log.e("DOWNLOADED ! ", "doWork: ");
                    return Result.success();
                } else
                    return Result.retry();
            } else {
                return Result.success();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }
    }


    private boolean shouldUpdate() throws IOException {
        Request request = new Request.Builder().url(versionURL).build();
        Response res = client.newCall(request).execute();
        if (res.isSuccessful()) {
            helper.updateDatabaseVersion(res.body().string());
            return true;
        } else {
            return false;
        }
    }
}
