package com.technawabs.cityservices.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStore {


    private static final String TAG = "UserStore";
    private static final String PREFERENCE_NAME = "UserStore";
    public static final String USER_NAME = "name";
    public static final String USER_MOBILE = "mobile";
    public static final String USER_TOKEN = "authToken";
    public static final String BALANCE = "120";
    private static SharedPreferences.Editor editor;

    public UserStore(Context context) {

        editor = getSharedPreferences(context).edit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserName(Context context, String name) {
        editor = getSharedPreferences(context).edit();
        editor.putString(USER_NAME, name).apply();
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(USER_NAME, "");
    }

    public static void setUserBalance(Context context, String balance) {
        editor = getSharedPreferences(context).edit();
        editor.putString(BALANCE, balance).apply();
    }

    public static String getUserBalance(Context context) {
        return getSharedPreferences(context).getString(BALANCE , "");
    }

    public static void setUserMobile(Context context, String userMobile) {
        editor = getSharedPreferences(context).edit();
        editor.putString(USER_MOBILE, userMobile).apply();
    }

    public static String getUserMobile(Context context) {
        return getSharedPreferences(context).getString(USER_MOBILE, "");
    }

    public static void setUserToken(Context context,String userToken) {
        editor = getSharedPreferences(context).edit();
        editor.putString(USER_TOKEN, userToken).apply();
    }

    public static String getUserToken(Context context) {
        return getSharedPreferences(context).getString(USER_TOKEN,"");
    }
}
