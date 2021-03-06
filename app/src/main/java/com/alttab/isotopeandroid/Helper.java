package com.alttab.isotopeandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.util.Map;
import java.util.WeakHashMap;

public class Helper {


    private static final String INITIALIZED = "KEY_INIT";
    private static final String IS_DARK_MODE = "KEY_DARK_MODE";
    private static final String DATABASE_VERSION = "DATABASE_VERSION";

    private static final String MAJOR_ID = "KEY_MAJOR";
    private static final String SUBGROUP_KEY = "KEY_SUBGROUP";


    public String getLastDatabaseVersion() {
        return preferences.getString(DATABASE_VERSION, "default version");
    }

    public boolean updateDatabaseVersion(String version) {
        String previous = getLastDatabaseVersion();
        if (!previous.equals(version) && !version.isEmpty()) {
            editor.putString(DATABASE_VERSION, version);
            editor.commit();
            return true;
        }

        return false;
    }

    public static Helper _instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final int DarkTheme = R.style.AppThemeDark;
    private static final int LightTheme = R.style.AppTheme;

    public static synchronized Helper getInstance(Context app) {
        if (_instance == null) {
            SharedPreferences sharedPreferences =
                    app.getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (!sharedPreferences.contains(INITIALIZED)) {
                editor.putBoolean(INITIALIZED, false);
                editor.putString(DATABASE_VERSION, "");
                editor.putBoolean(IS_DARK_MODE, false);
                editor.commit();
            }
            _instance = new Helper(sharedPreferences, editor);
        }
        return _instance;
    }

    public boolean getIsInitialized() {
        return preferences.getBoolean(INITIALIZED, false);
    }

    public void setIsInitialized(boolean init) {
        editor.putBoolean(INITIALIZED, init);
        editor.commit();
    }

    private Helper(SharedPreferences sp, SharedPreferences.Editor ed) {
        this.preferences = sp;
        this.editor = ed;
    }

    public boolean isDarkMode() {
        return preferences.getBoolean(IS_DARK_MODE, false);
    }

    private void toggleDarkMode() {
        boolean _isDarkMode = !isDarkMode();
        editor.putBoolean(IS_DARK_MODE, _isDarkMode);
        editor.commit();
    }

    public int getThemeStyle() {
        return isDarkMode() ? DarkTheme : LightTheme;
    }

    public String getMajorId() {
        return preferences.getString(MAJOR_ID, null);
    }

    public void setMajorId(String majorId) {
        editor.putString(MAJOR_ID, majorId);
        editor.commit();
    }

    public void setSubgroup(int subgroup) {
        editor.putInt(SUBGROUP_KEY, subgroup);
        editor.commit();
    }

    public int getSubgroup() {
        return preferences.getInt(SUBGROUP_KEY, 1);
    }

    public void resetMajor() {
        editor.remove(MAJOR_ID);
        editor.putBoolean(INITIALIZED, false);
        editor.commit();
    }

    public void hideKeyboard(Activity act) {
        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void hideSystemUI(Window w) {
        w.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        w.setStatusBarColor(R.attr.BackgroundColor);
    }

    public void toggleActivityTheme(Activity activity) {
        toggleDarkMode();
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    public abstract class DebouncedOnClickListener implements View.OnClickListener {

        private final long minimumInterval;
        private Map<View, Long> lastClickMap;

        /**
         * Implement this in your subclass instead of onClick
         *
         * @param v The view that was clicked
         */
        public abstract void onDebouncedClick(View v);

        /**
         * The one and only constructor
         *
         * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
         */
        public DebouncedOnClickListener(long minimumIntervalMsec) {
            this.minimumInterval = minimumIntervalMsec;
            this.lastClickMap = new WeakHashMap<View, Long>();
        }

        @Override
        public void onClick(View clickedView) {
            Long previousClickTimestamp = lastClickMap.get(clickedView);
            long currentTimestamp = SystemClock.uptimeMillis();

            lastClickMap.put(clickedView, currentTimestamp);
            if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > minimumInterval)) {
                onDebouncedClick(clickedView);
            }
        }
    }

}