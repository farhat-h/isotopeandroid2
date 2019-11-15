package com.alttab.isotopeandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.alttab.isotopeandroid.Adapters.MajorAutocompleteAdapter;
import com.alttab.isotopeandroid.Tasks.MajorListLoader;
import com.alttab.isotopeandroid.database.Major;
import com.alttab.isotopeandroid.database.Repository;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AutoCompleteTextView mMajorSelect;
    private Repository mRepo;
    private static final int LOADER_ID = 242;
    private Helper helper;
    private MotionLayout mMotionLayout;
    private static final int STATE_SELECT_GROUP = 1;
    private static final int STATE_INITIAL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = Helper.getInstance(this);
        setTheme(helper.getThemeStyle());
        helper.hideSystemUI(this.getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepo = new Repository(getApplication());
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, getCallbacks());
        mMotionLayout = findViewById(R.id.subgroup_motionLayout);
    }


    private LoaderManager.LoaderCallbacks getCallbacks() {
        return new LoaderManager.LoaderCallbacks<List<Major>>() {
            @NonNull
            @Override
            public Loader<List<Major>> onCreateLoader(int id, @Nullable Bundle args) {
                return new MajorListLoader(MainActivity.this, mRepo);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<Major>> loader, List<Major> data) {
                setupAutoComplete(data);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<Major>> loader) {
                Log.i("reset", "onLoaderReset");
            }
        };
    }

    private void setupAutoComplete(List<Major> majorList) {
        mMajorSelect = findViewById(R.id.majors_autocomplete);
        mMajorSelect.setThreshold(1);
        MajorAutocompleteAdapter adapter = new MajorAutocompleteAdapter(this, R.layout.major_autocomplete_item_layout, majorList);
        mMajorSelect.setAdapter(adapter);
        mMajorSelect.setOnItemClickListener(this);
    }

    public void toggleTheme(View view) {
        helper.toggleActivityTheme(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Major major = (Major) parent.getItemAtPosition(position);

        TextView tvTitle = findViewById(R.id.pick_major_title);
        tvTitle.setText(major.fullName);

        helper.setMajorId(major.majorId);
        setUIState(STATE_SELECT_GROUP);

        //Intent intent = new Intent(this, ScheduleActivity.class);
        //startActivity(intent);
    }

    private void setUIState(final int state) {
        switch (state) {
            case STATE_INITIAL:
                break;
            case STATE_SELECT_GROUP:
                _setSelectGroupUI();
        }
    }

    private void _setSelectGroupUI() {
        mMotionLayout.setVisibility(View.VISIBLE);
    }

    private void setSubGroup(int subGroup) {
        helper.setSubgroup(subGroup);
    }

    public void handleSelectG1(View view) {
        setSubGroup(1);
        mMotionLayout.transitionToStart();
    }

    public void handleSelectG2(View view) {
        setSubGroup(2);
        mMotionLayout.transitionToEnd();

    }
}
