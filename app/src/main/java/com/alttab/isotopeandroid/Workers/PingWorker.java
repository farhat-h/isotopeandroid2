package com.alttab.isotopeandroid.Workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alttab.isotopeandroid.Helper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PingWorker extends Worker {
    private Helper _helper;
    private OkHttpClient client;
    private Context mContext;
    private final static String versionUrl = "http://137.74.192.93/api/version";
    private final static String downloadUrl = "http://137.74.192.93/api/getLastDatabaseVersion";


    public PingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        _helper = Helper.getInstance(context);
        client = new OkHttpClient();
        mContext = context;

    }

    @NonNull
    @Override
    public Result doWork() {
        getRemoteDatabaseVersion();
        return Result.success();
    }

    private void downloadDatabase() {
        Request httpRequest = new Request.Builder().url(downloadUrl).build();
        client.newCall(httpRequest).enqueue(new DownloadDatabaseCallback());
    }

    private void getRemoteDatabaseVersion() {
        Request httpRequest = new Request.Builder().url(versionUrl).addHeader(
                "Content-Type",
                "application/octet-stream").build();
        client.newCall(httpRequest).enqueue(new VersionVerificationCallback());
    }


    private class VersionVerificationCallback implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            // update version
            if (response.isSuccessful() && response.body() != null) {
                String potentialNextVersion = response.body().string();
                boolean updateSuccessfull = _helper.updateDatabaseVersion(potentialNextVersion);
                if (updateSuccessfull) {
                    downloadDatabase();
                }
            }
        }
    }

    private class DownloadDatabaseCallback implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            File dst = new File(mContext.getFilesDir(), "database.sqlite");
            if (response.isSuccessful()) {
                InputStream in = response.body().byteStream();
                OutputStream out = new FileOutputStream(dst);
                try {
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } finally {
                    out.close();
                }
            }
        }
    }
}
