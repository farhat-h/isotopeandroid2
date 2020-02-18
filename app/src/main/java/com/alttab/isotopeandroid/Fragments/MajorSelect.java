package com.alttab.isotopeandroid.Fragments;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.MajorAutocompleteAdapter;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.Tasks.GeneralAsyncTask;
import com.alttab.isotopeandroid.Tasks.GeneralAsyncTaskCallbacks;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.utils.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MajorSelect extends Fragment implements AdapterView.OnItemClickListener, GeneralAsyncTaskCallbacks {

    private AutoCompleteTextView input;
    private Util tools;
    private GeneralAsyncTask task;
    private List<Major> majors;
    private MajorSelectCallbacks callbacks;

    public MajorSelect(Application application, MajorSelectCallbacks callbacks) {

        tools = Util.getExtendedInstance(application);
        this.callbacks = callbacks;

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_major_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task = new GeneralAsyncTask(this);
        task.execute();
        input = view.findViewById(R.id.majors_autocomplete);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Major major = (Major) parent.getItemAtPosition(position);
        tools.preferenceManager.setMajorId(major.majorId, major.fullName);
        callbacks.onMajorSelected();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute() {

        input.setThreshold(1);
        MajorAutocompleteAdapter adapter = new MajorAutocompleteAdapter(getContext(), R.layout.major_autocomplete_item_layout, majors);
        input.setAdapter(adapter);
        input.setOnItemClickListener(this);

    }

    @Override
    public void doInBackground() {
        majors = tools.repo.getAllMajors();
    }
}
