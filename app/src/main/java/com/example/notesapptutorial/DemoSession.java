package com.example.notesapptutorial;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * In-memory demo login. Any credentials are accepted; nothing is sent to a server.
 */
public class DemoSession {

    private static final String PREFS = "demo_session";
    private static final String KEY_LOGGED_IN = "logged_in";

    public static void login(Context context) {
        prefs(context).edit().putBoolean(KEY_LOGGED_IN, true).apply();
    }

    public static void logout(Context context) {
        prefs(context).edit().clear().apply();
    }

    public static boolean isLoggedIn(Context context) {
        return prefs(context).getBoolean(KEY_LOGGED_IN, false);
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
