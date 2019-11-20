package com.alttab.isotopeandroid.Fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.Adapters.AlternativesListAdapter;
import com.alttab.isotopeandroid.Helper;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.ScheduleActivity;
import com.alttab.isotopeandroid.Tasks.AdaptiveLoaderCallbacks;
import com.alttab.isotopeandroid.Tasks.AdaptiveTaskLoad;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.NamedSession;
import com.alttab.isotopeandroid.database.Repository;
import com.alttab.isotopeandroid.database.Session;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlternativesSheetFragment extends BottomSheetDialogFragment {

    private String time;
    private RecyclerView altRecycler;
    private TextView tvDay, tvTime;
    private List<NamedSession> alternatives;
    private Major major;
    private AdaptiveLoaderCallbacks<List<NamedSession>> callbacks;
    private Context mContext;
    private int day;

    private View emptyState;

    public AlternativesSheetFragment(final int day, final String time, Context context) {
        this.day = day;
        this.time = time;
        this.major = ScheduleActivity.currentlySelectedMajor;
        this.mContext = context;
        this.callbacks = new AdaptiveLoaderCallbacks<List<NamedSession>>() {
            @Override
            public void onExecute(WeakReference<Repository> wrRepo) {
                alternatives = wrRepo.get().getAlternativeSessions(day, major.majorId, time, major.majorName, major.year);
            }

            @Override
            public void onPostExecute() {
                setupRecyclerView();
            }
        };
    }

    private void setupRecyclerView() {
        if (alternatives.size() > 0) {
            this.altRecycler.setLayoutManager(new LinearLayoutManager(mContext));
            this.altRecycler.setAdapter(new AlternativesListAdapter(mContext, alternatives));
        } else {
            this.altRecycler.setVisibility(View.GONE);
            this.emptyState.setVisibility(View.VISIBLE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View sheet = inflater.inflate(R.layout.alternatives_sheet, container, false);

        tvDay = sheet.findViewById(R.id.alternatives_day);
        tvTime = sheet.findViewById(R.id.alternatives_time);
        altRecycler = sheet.findViewById(R.id.alternatives_recycler);
        emptyState = sheet.findViewById(R.id.alternatives_empty_state);

        tvDay.setText(getDayString(day));
        tvTime.setText(time);
        Helper helper = Helper.getInstance(mContext);
        helper.hideSystemUI(getActivity().getWindow());
        return sheet;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        AdaptiveTaskLoad loader = new AdaptiveTaskLoad(ScheduleActivity.mRepo, callbacks);
        ExecutorService pool = Executors.newFixedThreadPool(16);
        loader.executeOnExecutor(pool);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Helper.getInstance(mContext).hideSystemUI(getActivity().getWindow());
        super.onDismiss(dialog);
    }

    private static String getDayString(int index) {
        final String[] days = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        if (index > 0) {
            return days[index - 1];

        }
        return null;
    }
}
