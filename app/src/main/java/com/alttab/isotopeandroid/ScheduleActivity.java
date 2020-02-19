package com.alttab.isotopeandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.DayPageViewAdapter;
import com.alttab.isotopeandroid.Tasks.GeneralAsyncTask;
import com.alttab.isotopeandroid.Tasks.GeneralAsyncTaskCallbacks;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Regime;
import com.alttab.isotopeandroid.utils.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private MotionLayout motionLayout;
    public static Major currentlySelectedMajor;
    private Regime currentRegime;
    private TextView tvRegime;
    private ImageView ctxMenu;
    private Util tools;
    private PopupMenu menu;

    private String DAY_NUMBER_STATE_KEY="CACHED_SELECTED_DAY";
    private int savedDayNumber=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedDayNumber = -1;
        if(savedInstanceState!=null){
            savedDayNumber = savedInstanceState.getInt(DAY_NUMBER_STATE_KEY);
        }

        tools = Util.getExtendedInstance(getApplication());
        setTheme(tools.preferenceManager.theme());
        // @TODO implement UI Manager
//        helper.hideSystemUI(this.getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        motionLayout = findViewById(R.id.motion_layout);
        appBarLayout = findViewById(R.id.appBarLayout);
        ctxMenu = findViewById(R.id.schedule_contextual_menu);
        tvRegime = findViewById(R.id.schedule_regime);
        appBarLayout.setOutlineProvider(null);
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float percent = -verticalOffset / (float) appBarLayout.getTotalScrollRange();
            motionLayout.setProgress(percent);
        });

        final TextView majorName = findViewById(R.id.major_name);
        GeneralAsyncTask loadingTask = new GeneralAsyncTask(new GeneralAsyncTaskCallbacks() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute() {
                majorName.setText(tools.preferenceManager.majorName());
                tvRegime.setText(currentRegime.toString());
            }

            @Override
            public void doInBackground() {
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH) + 1;// 0 based so add 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                currentRegime = tools.repo.getCurrentRegime(day, month);
                currentlySelectedMajor = tools.repo.getMajorById(tools.preferenceManager.majorId());
            }
        });
        loadingTask.execute();
        setupLayout();

        prepareContextMenu();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(DAY_NUMBER_STATE_KEY,tabLayout.getSelectedTabPosition());
        super.onSaveInstanceState(outState);
    }

    private int getDayNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public void backToMajorSelect(View view) {
        tools.preferenceManager.resetMajor();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((TextView) findViewById(R.id.schedule_version)).setText(tools.preferenceManager.dbversion().replaceAll(".sqlite$", ""));
    }

    private void setupLayout() {
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2.setAdapter(new DayPageViewAdapter(this));
        TabLayoutMediator tabViewMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(Constants.DAYS[position]));

        tabViewMediator.attach();
        int dayNumber = savedDayNumber>-1 ? savedDayNumber : getDayNumber() - 2;
        dayNumber = dayNumber % 7;

        if (tabLayout.getTabAt(dayNumber) != null) {
            tabLayout.getTabAt(dayNumber).select();
            tabLayout.setScrollPosition(dayNumber, 0f, true);
            viewPager2.setCurrentItem(dayNumber, false);
        }
    }

    public void showCtxMenu(View view) {
        menu.show();
    }

    private void prepareContextMenu() {
        menu = new PopupMenu(this, ctxMenu);
        menu.inflate(R.menu.contextual_menu);
        MenuPopupHelper helper = new MenuPopupHelper(getApplicationContext(), (MenuBuilder) menu.getMenu(), ctxMenu);
        helper.setForceShowIcon(true);
    }
}
