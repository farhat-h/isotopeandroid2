package com.alttab.isotopeandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alttab.isotopeandroid.R;

public class PreferenceManager {

    private static final String K_THEME = "K_THEME";
    private static final String K_DBVERSION = "K_DBVERSION";
    private static final String K_MAJORID = "K_MAJORID";
    private static final String K_SUBGROUP = "K_SUBGROUP";
    private static final String K_PREFS_TAG = "K_PREFS_TAG";
    private static final String K_MAJORNAME = "K_MAJORNAME";

    public static final int THEME_DARK = R.style.AppThemeDark;
    public static final int THEME_LIGHT = R.style.AppTheme;


    private SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(K_PREFS_TAG, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor editor() {
        return prefs.edit();
    }

    public int theme() {
        return prefs.getInt(K_THEME, THEME_LIGHT);
    }

    public void toggleTheme() {
        int theme = theme();
        int targetTheme = theme == THEME_LIGHT ? THEME_DARK : THEME_LIGHT;
        editor().putInt(K_THEME, targetTheme).commit();
    }


    public String majorId() {
        return prefs.getString(K_MAJORID, "");
    }

    public String majorName() {
        return prefs.getString(K_MAJORNAME, "");
    }

    public void setMajorId(String majorId, String majorFullName) {
        editor()
                .putString(K_MAJORID, majorId)
                .putString(K_MAJORNAME, majorFullName)
                .commit();
    }


    public int subgroup() {
        return prefs.getInt(K_SUBGROUP, -1);
    }

    public void setSubgroup(int subgroup) {
        editor().putInt(K_SUBGROUP, subgroup).commit();
    }


    public String dbversion() {
        return prefs.getString(K_DBVERSION, "");
    }

    public void setDbversion(String dbversion) {
        if (!dbversion.isEmpty() && !dbversion.equals(dbversion()))
            editor().putString(K_DBVERSION, dbversion).commit();
    }

    public void resetMajor() {
        editor().remove(K_MAJORID)
                .remove(K_SUBGROUP)
                .commit();
    }
}
