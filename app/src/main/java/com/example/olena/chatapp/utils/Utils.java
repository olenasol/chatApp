package com.example.olena.chatapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.olena.chatapp.R;

public class Utils {

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_GREEN = 1;
    public final static int THEME_YELLOW = 2;
    public final static int THEME_RED = 3;


    public static void onActivityCreateSetTheme(Activity activity) {
        int sTheme = 0;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sTheme = preferences.getInt(Constants.THEME_NUMBER, 0);
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.GreenTheme);
                break;
            case THEME_YELLOW:
                activity.setTheme(R.style.YellowTheme);
                break;
            case THEME_RED:
                activity.setTheme(R.style.RedTheme);
                break;
        }
    }

}