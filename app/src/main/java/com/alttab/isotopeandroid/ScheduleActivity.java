package com.alttab.isotopeandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.loader.app.LoaderManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.alttab.isotopeandroid.Adapters.DayPageViewAdapter;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Repository;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ScheduleActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private MotionLayout motionLayout;
    public static Repository mRepo;

    public static LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Helper helper = Helper.getInstance(this);
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

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        mLoaderManager = LoaderManager.getInstance(this);

        viewPager2.setAdapter(new DayPageViewAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(Constants.DAYS[position]);
            }
        }).attach();
    }

}
