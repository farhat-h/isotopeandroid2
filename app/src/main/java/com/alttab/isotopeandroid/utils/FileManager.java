package com.alttab.isotopeandroid.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {

    public static String DB_FNAME = "database.sqlite";
    private File databaseFile;

    public FileManager(Context context) {
        this.databaseFile = new File(context.getFilesDir(), DB_FNAME);
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected File getDatabaseFile() {
        return databaseFile;
    }

    public void override(InputStream in) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(databaseFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}
