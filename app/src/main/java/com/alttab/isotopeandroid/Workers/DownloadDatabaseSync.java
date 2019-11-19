package com.alttab.isotopeandroid.Workers;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadDatabaseSync {
    private static final String downloadUrl = "http://137.74.192.93/api/getLastDatabaseVersion";
    private OkHttpClient client;
    private File databaseFile;

    public DownloadDatabaseSync(OkHttpClient client, Context context) {
        this.client = client;
        databaseFile = new File(context.getFilesDir(), "database.sqlite");
    }

    public DownloadDatabaseSync(Context mContext) {
        this.client = new OkHttpClient();
        this.databaseFile = new File(mContext.getFilesDir(), "database.sqlite");
    }

    public boolean download(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            copyStreamToFile(response.body().byteStream(), databaseFile);
            return true;
        } else
            return false;
    }

    public boolean download() throws IOException {
        return download(downloadUrl);
    }

    private void copyStreamToFile(InputStream in, FileOutputStream out) {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyStreamToFile(InputStream in, File out) throws FileNotFoundException {
        copyStreamToFile(in, new FileOutputStream(out));
    }
}
