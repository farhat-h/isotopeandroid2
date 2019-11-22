package com.alttab.isotopeandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.DayPageViewAdapter;
import com.alttab.isotopeandroid.Tasks.AdaptiveLoaderCallbacks;
import com.alttab.isotopeandroid.Tasks.AdaptiveTaskLoad;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Regime;
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
    private Regime currentRegime;
    private TextView tvRegime;

    /*

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = Helper.getInstance(this);
        setTheme(helper.getThemeStyle());
        helper.hideSystemUI(this.getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mRepo = new Repository(getApplication());
        mRepo.setSubgroup(helper.getSubgroup());
        motionLayout = findViewById(R.id.motion_layout);
        appBarLayout = findViewById(R.id.appBarLayout);

        tvRegime = findViewById(R.id.schedule_regime);
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
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH) + 1;// 0 based so add 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                currentRegime = wrRepo.get().getCurrentRegime(day, month);
            }

            @Override
            public void onPostExecute() {
                if (currentlySelectedMajor != null)
                    majorName.setText(currentlySelectedMajor.fullName);
                if (currentRegime != null) {

                    String stringBuilder = currentRegime.regimeQAB +
                            (!currentRegime.regimeZ.equals("null") ? " " + currentRegime.regimeZ : "") +
                            (!currentRegime.regimeM.equals("null") ? " " + currentRegime.regimeM : "");
                    tvRegime.setText(stringBuilder);
                }
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
        int dayNumber = getDayNumber() - 2;
        dayNumber = dayNumber % 7;
        if (tabLayout.getTabAt(dayNumber) != null)
            tabLayout.getTabAt(dayNumber).select();
        tabLayout.setScrollPosition(dayNumber, 0f, true);
        viewPager2.setCurrentItem(dayNumber, false);
    }

    private int getDayNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public void backToMajorSelect(View view) {
        helper.resetMajor();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((TextView) findViewById(R.id.schedule_version)).setText(helper.getLastDatabaseVersion().replaceAll(".sqlite$", ""));
    }

    public void scheduleToggleTheme(View view) {
        helper.toggleActivityTheme(this);
    }
}
