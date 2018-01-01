package com.example.olena.chatapp.additional_classes;

import android.app.Activity;

import com.example.olena.chatapp.R;

public class Utils
{
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_GREEN = 1;
    public final static int THEME_YELLOW = 2;
    public final static int THEME_RED = 3;
    public static void changeToTheme(int theme)
    {
        sTheme = theme;
    }
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
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

    public static int getTheme() {
        return sTheme;
    }
}