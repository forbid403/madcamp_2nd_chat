package com.example.bitgaram.main.bitgaram.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;

public class EnvironmentData {
    public static final String PREFERENCENAME = "saveData";
    public static EnvironmentData singleton;
    public static String phoneNumber;

    public static EnvironmentData newInstance(Context context) {
        if(singleton == null) {
            singleton = new EnvironmentData();
        }
        singleton.LoadEnvironment(context);

        return singleton;
    }

    private EnvironmentData() {

    }

    public static void SaveEnvironment(Context contex) {
        SharedPreferences pref = contex.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("phoneNumber", phoneNumber);
        editor.commit();
    }

    public static void LoadEnvironment(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);

        phoneNumber = pref.getString("phoneNumber", "");
    }
}
