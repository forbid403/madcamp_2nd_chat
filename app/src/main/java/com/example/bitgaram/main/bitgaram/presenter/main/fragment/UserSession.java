package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class UserSession {

    public static final String PREF_NAME = "UserInfo";
    public static final String name = "Name";
    public static final String message = "Message";
    public static final String phone = "Phone";
    public static final String profilePicture = "ProfilePicture";
    public static final String open = "Open";
    public static final String isSignUp = "isSignUp";

    SharedPreferences sf;
    SharedPreferences.Editor editor;
    Context mContext = null;
    InformationData mData;

    public UserSession(Context context, InformationData data) {
        mContext = context;
        sf = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sf.edit();
        mData = data;
    }

    public void createUserSession(){
        editor.putString(name, mData.name);
        editor.putString(message, mData.message);
        editor.putString(phone, mData.phone);
        editor.putString(profilePicture, mData.profilePicture);
        editor.putBoolean(open, mData.open);
        editor.putBoolean(isSignUp, true);
        editor.commit();
    }

    public boolean isUserSignUp(){
        return sf.getBoolean(isSignUp, false);
    }

    public boolean checkSignUp(){
        if(!this.isUserSignUp()){
            Intent i = new Intent(mContext, SignUpActivity.class);
            mContext.startActivity(i);
            return true;
        }

        return false;
    }

}
