package com.alttab.isotopeandroid.Fragments;


import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.ScheduleActivity;
import com.alttab.isotopeandroid.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubgroupSelector extends Fragment {


    Util tools;
    MajorSelectCallbacks callbacks;
    MotionLayout layout;

    public SubgroupSelector(Application application, MajorSelectCallbacks callbacks) {
        this.callbacks = callbacks;
        tools = Util.getExtendedInstance(application);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subgroup_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView pickedMajor = view.findViewById(R.id.selected_major_name);
        pickedMajor.setText(tools.preferenceManager.majorName());
        layout = view.findViewById(R.id.subgroup_select_layout);

        TextView confirmBtn = view.findViewById(R.id.btn_confirm_subgroup);
        TextView cancelBtn = view.findViewById(R.id.btn_cancel_subgroup);

        TextView subgroup1 = view.findViewById(R.id.subgroup_1);
        TextView subgroup2 = view.findViewById(R.id.subgroup_2);


        confirmBtn.setOnClickListener(e -> confirmSubGroup());
        cancelBtn.setOnClickListener(e -> cancel());

        subgroup1.setOnClickListener(e -> selectSubgroupOne());
        subgroup2.setOnClickListener(e -> selectSubgroupTwo());
    }

    private void confirmSubGroup() {
        callbacks.onSubgroupSelected();
    }

    private void selectSubgroupOne() {
        tools.preferenceManager.setSubgroup(1);
        layout.transitionToStart();
    }

    private void selectSubgroupTwo() {
        tools.preferenceManager.setSubgroup(2);
        layout.transitionToEnd();
    }

    private void cancel() {
        tools.preferenceManager.resetMajor();
    }

}
