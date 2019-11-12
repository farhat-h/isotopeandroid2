package com.alttab.isotopeandroid.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alttab.isotopeandroid.Constants;
import com.alttab.isotopeandroid.Fragments.DayFragment;

public class DayPageViewAdapter extends FragmentStateAdapter {

    public DayPageViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DayFragment dayFragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(DayFragment.DAY_KEY, (position + 1));
        dayFragment.setArguments(args);
        return dayFragment;
    }

    @Override
    public int getItemCount() {
        return Constants.DAYS.length;
    }
}
