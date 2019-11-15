package com.alttab.isotopeandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.DayPageViewAdapter;
import com.alttab.isotopeandroid.Tasks.AdaptiveLoaderCallbacks;
import com.alttab.isotopeandroid.Tasks.AdaptiveTaskLoad;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Repository;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private MotionLayout motionLayout;
    public static Repository mRepo;
    public static Major currentlySelectedMajor;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = Helper.getInstance(this);
        setTheme(helper.getThemeStyle());
        helper.hideSystemUI(this.getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mRepo = new Repository(getApplication());

        motionLayout = findViewById(R.id.motion_layout);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setOutlineProvider(null);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = -verticalOffset / (float) appBarLayout.getTotalScrollRange();
                motionLayout.setProgress(percent);
            }
        });


        final TextView majorName = findViewById(R.id.major_name);
        final AdaptiveTaskLoad task = new AdaptiveTaskLoad(mRepo, null);
        task.setCallbacks(new AdaptiveLoaderCallbacks<Major>() {
            @Override
            public void onExecute(WeakReference<Repository> wrRepo) {
                String majorId = helper.getMajorId();
                currentlySelectedMajor = wrRepo.get().getMajorById(majorId);
            }

            @Override
            public void onPostExecute() {
                if (currentlySelectedMajor != null)
                    majorName.setText(currentlySelectedMajor.fullName);
            }
        });
        task.execute();
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager2.setAdapter(new DayPageViewAdapter(this));
        TabLayoutMediator tabViewMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(Constants.DAYS[position]);
            }

        });

        tabViewMediator.attach();
        tabLayout.getTabAt(getDayNumber() - 2).select();
        tabLayout.setScrollPosition(getDayNumber() - 2, 0f, true);

    }

    private int getDayNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public void backToMajorSelect(View view) {
        helper.resetMajor();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
