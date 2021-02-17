package com.example.parkingapp.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.parkingapp.activity.SignInActivity;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class PreferenceSettings {

    private final String LOGIN = "login";
    private final String userName = "username";
    private final String userEmail = "email";
    private final String userID = "userID";
    private final Context context;
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final String TAG = this.getClass().getCanonicalName();

    public PreferenceSettings(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(TAG, context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setIslogin(boolean flag) {
        editor.putBoolean(LOGIN, flag).commit();
    }

    public boolean getIsLogin() {
        return sp.getBoolean(LOGIN, false);
    }


    public void logoutUser() {
        try {
            context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(LOGIN).apply();
            context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(userEmail).apply();
            context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(userID).apply();
            context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().remove(userName).apply();

            Intent i = new Intent(context, SignInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return sp.getString(this.userName, "");
    }

    public void setUserName(String userName) {
        sp.edit().putString(this.userName, userName).apply();
    }

    public String getUserEmail() {
        return sp.getString(this.userEmail, "");
    }

    public void setUserEmail(String userEmail) {
        sp.edit().putString(this.userEmail, userEmail).apply();
    }

    public String getUserID() {
        return sp.getString(userID, "");

    }

    public void setUserID(String id) {
        sp.edit().putString(userID, id).apply();

    }


}
