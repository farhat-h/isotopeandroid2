package com.alttab.isotopeandroid.Fragments;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alttab.isotopeandroid.Adapters.SessionRecyclerViewAdapter;
import com.alttab.isotopeandroid.Helper;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.ScheduleActivity;
import com.alttab.isotopeandroid.Tasks.CustomSessionLoader;
import com.alttab.isotopeandroid.Tasks.SessionLoaderCallbacks;
import com.alttab.isotopeandroid.database.Session;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment implements SessionLoaderCallbacks<List<Session>> {

    public static String DAY_KEY = "DAY_KEY";
    private RecyclerView recyclerView;
    private int currentDay;
    private SessionRecyclerViewAdapter adapter;

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.currentDay = getArguments().getInt(DAY_KEY, 1);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        recyclerView = view.findViewById(R.id.sessions_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SessionRecyclerViewAdapter(getContext(), null);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomSessionLoader loader = new CustomSessionLoader(currentDay, Helper.getInstance(getContext()).getMajorId(), ScheduleActivity.mRepo, this);
        loader.execute();
    }


    @Override
    public void onResults(List<Session> objects) {
    }

    @Override
    public void onTaskDone(List<Session> objects) {
        adapter.updateData(objects);
    }
}
