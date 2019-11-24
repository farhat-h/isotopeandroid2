package com.alttab.isotopeandroid.Adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alttab.isotopeandroid.Fragments.MajorSelect;
import com.alttab.isotopeandroid.Fragments.MajorSelectCallbacks;
import com.alttab.isotopeandroid.Fragments.SubgroupSelector;

public class MainPageAdapter extends FragmentStateAdapter {
    private Application application;
    private MajorSelectCallbacks callbacks;

    public MainPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setCallbacks(MajorSelectCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        assert application != null;
        assert callbacks != null;

        if (position == 0)
            return new MajorSelect(application, callbacks);
        else
            return new SubgroupSelector(application, callbacks);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
