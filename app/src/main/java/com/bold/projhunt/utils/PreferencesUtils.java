package com.bold.projhunt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class PreferencesUtils {

    private static final String PREFERENCES_IS_FIRST_INSTALL = "preferences_is_first_install";

    private static final String PREFERENCES_CLIENT_TOKEN  = "preferences_client_token";
    private static final String PREFERENCES_TOKEN_EXPIRES  = "preferences_token_expires";

    public static boolean getIsFirstInstall(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean(PREFERENCES_IS_FIRST_INSTALL, true);
    }

    public static boolean setIsFirstInstall(Context context, boolean isFirstInstall) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.edit().putBoolean(PREFERENCES_IS_FIRST_INSTALL, isFirstInstall).commit();
    }

    public static String getClientToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(PREFERENCES_CLIENT_TOKEN, "");
    }

    public static boolean setClientToken(Context context, String clientToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.edit().putString(PREFERENCES_CLIENT_TOKEN, clientToken).commit();
    }

    public static String getTokenExpiration(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(PREFERENCES_TOKEN_EXPIRES, "");
    }

    public static boolean setTokenExpiration(Context context, String clientToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.edit().putString(PREFERENCES_TOKEN_EXPIRES, clientToken).commit();
    }
}
