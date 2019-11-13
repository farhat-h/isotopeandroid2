package com.alttab.isotopeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.AlternativesListAdapter;
import com.alttab.isotopeandroid.Tasks.AdaptiveLoaderCallbacks;
import com.alttab.isotopeandroid.Tasks.AdaptiveTaskLoad;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Repository;
import com.alttab.isotopeandroid.database.Session;

import java.lang.ref.WeakReference;
import java.util.List;

public class Alternatives extends AppCompatActivity {
    public static final String MAJORID = "MAJORID";
    public static final String DAY = "DAY";
    public static final String TIME = "TIME";
    private Helper helper;
    private Repository wRepo;
    private List<Session> alternativeSessions;
    private RecyclerView mRecylerView;
    private TextView tvDay, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = Helper.getInstance(this);
        setTheme(helper.getThemeStyle());
        helper.hideSystemUI(this.getWindow());

        wRepo = new Repository(this.getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternatives);

        final int day = getIntent().getIntExtra(DAY, -1);
        final String time = getIntent().getStringExtra(TIME);


        mRecylerView = findViewById(R.id.alternatives_recycler);
        tvDay = findViewById(R.id.alternatives_day);
        tvTime = findViewById(R.id.alternatives_time);


        tvDay.setText(getDayString(day));
        tvTime.setText(time);

        AdaptiveTaskLoad task = new AdaptiveTaskLoad(wRepo, new AdaptiveLoaderCallbacks() {
            @Override
            public void onExecute(WeakReference _wr) {
                Major m = ScheduleActivity.currentlySelectedMajor;
                alternativeSessions = wRepo.getAlternativeSessions(day, m.majorId, time, m.majorName, m.year);
            }

            @Override
            public void onPostExecute() {
                setupRecyclerView(mRecylerView, alternativeSessions);
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setupRecyclerView(RecyclerView rv, List<Session> sessionList) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new AlternativesListAdapter(this, sessionList));
    }

    private static String getDayString(int index) {
        final String[] days = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        if (index > 0) {
            return days[index - 1];

        }
        return null;
    }
}
