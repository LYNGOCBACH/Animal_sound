package com.bach.animalsoundmvvm;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtils {
    private static final String PREF_FILE = "pref_shared";
    private static CommonUtils INSTANCE;

    private CommonUtils() {
        // for singleton
    }

    public static CommonUtils getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new CommonUtils();
        }
        return INSTANCE;
    }

    public void savePref(String key, String data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        if (key == null || key.isEmpty()) {
            return;
        }
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().putString(key, data).apply();
    }

    public void clearPref(String key) {
        if (key == null || key.isEmpty()) {
            return;
        }
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }

    public String getPref(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }
}
