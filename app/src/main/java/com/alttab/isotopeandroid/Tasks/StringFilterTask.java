package com.alttab.isotopeandroid.Tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class StringFilterTask extends AsyncTaskLoader<List<String>> {
    private Context mContext;

    public StringFilterTask(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        return null;
    }
}
