package com.alttab.isotopeandroid.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fetcher {
    private OkHttpClient client;

    private static final String hostName = "http://farhat-h.space";
    private static final String downloadUrl = hostName + "/api/getLastDatabaseVersion";
    private static final String versionURL = hostName +"/api/version";

    public Fetcher() {
        client = new OkHttpClient();
    }

    @Nullable
    public String remoteVersion() throws IOException {
        Request request = new Request.Builder().url(versionURL).build();
        Response res = client.newCall(request).execute();

        if (res.isSuccessful()) {
            return res.body().string();
        }
        return null;
    }

    @Nullable
    public InputStream remoteDatabaseAsStream() throws IOException {
        Request request = new Request.Builder().url(downloadUrl)
                .addHeader("Content-Type", "application/octet-stream")
                .build();
        Response res = client.newCall(request).execute();
        if (res.isSuccessful()) {
            return res.body().byteStream();
        }
        return null;
    }
}
