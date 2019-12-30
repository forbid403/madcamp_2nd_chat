package com.example.bitgaram.main.bitgaram.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;

public class Environment {
    public static final String PREFERENCENAME = "saveData";
    public static Environment singleton;
    public static String phoneNumber;

    public static Environment newInstance(Context context) {
        if(singleton == null) {
            singleton = new Environment();
        }
        singleton.LoadEnvironment(context);

        return singleton;
    }

    private Environment() {

    }

    public static void SaveEnvironment(Context contex) {
        SharedPreferences pref = contex.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("phoneNumber", phoneNumber);
    }

    public static void LoadEnvironment(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);

        phoneNumber = pref.getString("phoneNumber", "");
    }
}
