package com.bov.vitali.training.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Preferences {
    private static final String PREFERENCES = "pref.xml";
    private static final String TOKEN_TYPE = "token_type";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SCOPE = "scope";
    private static final String EXPIRES_AT = "expires_at";
    private static final String USER_ID = "user_id";

    private Preferences() {
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static String getTokenType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_TYPE, "");
    }

    public static void setTokenType(Context context, String tokenType) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_TYPE, tokenType);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(ACCESS_TOKEN, "");
    }

    public static void setAccessToken(Context context, String accessToken) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public static String getRefreshToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(REFRESH_TOKEN, "");
    }

    public static void setRefreshToken(Context context, String refreshToken) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public static Set<String> getScope(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        Set<String> scope = new HashSet<>();
        return prefs.getStringSet(SCOPE, scope);
    }

    public static void setScope(Context context, Set<String> scope) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SCOPE, scope);
        editor.apply();
    }

    public static long getExpiresAt(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getLong(EXPIRES_AT, 0);
    }

    public static void setExpiresAt(Context context, long expiresAt) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(EXPIRES_AT, expiresAt);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(USER_ID, "");
    }

    public static void setUserId(Context context, String userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public static void clear(Context context) {
        setTokenType(context, "");
        setAccessToken(context, "");
        setRefreshToken(context, "");
        setScope(context, null);
        setExpiresAt(context, 0);
        setUserId(context, "");
    }
}