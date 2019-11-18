package com.alttab.isotopeandroid;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
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
    private static final int STATE_SELECT_GROUP = 1;
    private static final int STATE_INITIAL = 0;

    @ColorInt
    private int backgroundColor;

    @ColorInt
    private int primaryTextColor;

    @ColorInt
    private int secondaryTextColor;
    private TextView tvGroup1, tvGroup2, tvTitle, tvSelectedMajor;
    private LinearLayout selectGroupLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = Helper.getInstance(this);
        setTheme(helper.getThemeStyle());
        helper.hideSystemUI(this.getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepo = new Repository(getApplication());
        mRepo.setSubgroup(helper.getSubgroup());
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, getCallbacks());

        tvGroup1 = findViewById(R.id.subgroup_1);
        tvGroup2 = findViewById(R.id.subgroup_2);
        tvTitle = findViewById(R.id.pick_major_title);
        tvSelectedMajor = findViewById(R.id.selected_major);
        selectGroupLayout = findViewById(R.id.group_select_container);
        tvSelectedMajor = findViewById(R.id.selected_major);


        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();

        theme.resolveAttribute(R.attr.BackgroundColor, typedValue, true);
        backgroundColor = typedValue.data;

        theme.resolveAttribute(R.attr.primaryTextColor, typedValue, true);
        primaryTextColor = typedValue.data;

        theme.resolveAttribute(R.attr.secondaryTextColor, typedValue, true);
        secondaryTextColor = typedValue.data;
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
        helper.setMajorId(major.majorId);
        helper.setSubgroup(1);

        helper.hideKeyboard(this);
        tvSelectedMajor.setText(major.fullName);
        tvTitle.setVisibility(View.GONE);
        mMajorSelect.setVisibility(View.GONE);
        selectGroupLayout.setVisibility(View.VISIBLE);
        tvSelectedMajor.setVisibility(View.VISIBLE);
    }

    private void _selectGroup(int group) {
        helper.setSubgroup(group);
    }

    public void selectGroup2(View view) {
        int subgroup = 2;
        _selectGroup(subgroup);
        tvGroup2.setBackgroundColor(backgroundColor);
        tvGroup2.setTextColor(primaryTextColor);

        tvGroup1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        tvGroup1.setTextColor(secondaryTextColor);
    }

    public void selectGroup1(View view) {
        int subgroup = 1;
        _selectGroup(subgroup);
        tvGroup1.setBackgroundColor(backgroundColor);
        tvGroup1.setTextColor(primaryTextColor);

        tvGroup2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        tvGroup2.setTextColor(secondaryTextColor);

        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
