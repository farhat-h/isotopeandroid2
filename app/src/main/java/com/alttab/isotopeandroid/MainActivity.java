package com.alttab.isotopeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.alttab.isotopeandroid.Adapters.MainPageAdapter;
import com.alttab.isotopeandroid.Fragments.MajorSelectCallbacks;


public class MainActivity extends AppCompatActivity implements MajorSelectCallbacks {
    private ViewPager2 pager;
    public static String K_J2SS = "K_J2SS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        boolean jumpToSubgroupSelect = intent.getBooleanExtra(K_J2SS, false);
        pager = findViewById(R.id.main_pager);

        MainPageAdapter pageAdapter = new MainPageAdapter(this);

        pageAdapter.setApplication(getApplication());
        pageAdapter.setCallbacks(this);

        pager.setAdapter(pageAdapter);
        pager.setUserInputEnabled(false);
        if (jumpToSubgroupSelect)
            pager.setCurrentItem(1, true);

    }


    @Override
    public void onMajorSelected() {
        pager.setCurrentItem(1, true);
    }

    @Override
    public void onSubgroupSelected() {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancelGroupSelect() {
        pager.setCurrentItem(0, true);
    }
}
