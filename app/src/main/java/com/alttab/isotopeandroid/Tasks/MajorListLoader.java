package com.alttab.isotopeandroid.Tasks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Repository;

import java.lang.ref.WeakReference;
import java.util.List;

public class MajorListLoader extends AsyncTaskLoader<List<Major>> {

    private WeakReference<Repository> repository;

    public MajorListLoader(@NonNull Context context, Repository wrRepo) {
        super(context);
        this.repository = new WeakReference<>(wrRepo);
    }


    @Override
    protected void onStartLoading() {
            forceLoad();
    }

    @Nullable
    @Override
    public List<Major> loadInBackground() {
        return this.repository.get().getAllMajors();
    }
}