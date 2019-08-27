package com.example.lunchrun.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedSharedPreference {

    static final String PREF_USER_EMAIL = "email";
    static final String PREF_USER_PWD = "password";
    static final String PREF_USER_TOKEN = "token";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.commit();
    }

    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserPassword(Context ctx, String pwd) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PWD, pwd);
        editor.commit();
    }

    public static String getUserPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PWD, "");
    }

    public static void setUserToken(Context ctx, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TOKEN, token);
        editor.commit();
    }

    public static String getUserToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_TOKEN, null);
    }

    public static void clearUser(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}