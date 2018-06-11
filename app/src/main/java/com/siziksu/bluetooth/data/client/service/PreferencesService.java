package com.siziksu.bluetooth.data.client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public final class PreferencesService {

    @Inject
    Context context;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferencesService(Context context) {
        this.context = context;
    }

    public PreferencesService useDefaultSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        createEditor();
        return this;
    }

    public PreferencesService usePreferences(String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        createEditor();
        return this;
    }

    public String getValue(String key, String defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getString(key, defaultValue);
    }

    public int getValue(String key, int defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getInt(key, defaultValue);
    }

    public long getValue(String key, long defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getLong(key, defaultValue);
    }

    public float getValue(String key, float defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getFloat(key, defaultValue);
    }

    public boolean getValue(String key, boolean defaultValue) {
        if (preferences == null) {
            return defaultValue;
        }
        return preferences.getBoolean(key, defaultValue);
    }

    public void applyValue(String key, String value) {
        if (preferences != null && editor != null) {
            editor.putString(key, value).apply();
        }
    }

    public void applyValue(String key, int value) {
        if (preferences != null && editor != null) {
            editor.putInt(key, value).apply();
        }
    }

    public void applyValue(String key, long value) {
        if (preferences != null && editor != null) {
            editor.putLong(key, value).apply();
        }
    }

    public void applyValue(String key, float value) {
        if (preferences != null && editor != null) {
            editor.putFloat(key, value).apply();
        }
    }

    public void applyValue(String key, boolean value) {
        if (preferences != null && editor != null) {
            editor.putBoolean(key, value).apply();
        }
    }

    public PreferencesService setValue(String key, String value) {
        if (preferences != null && editor != null) {
            editor.putString(key, value);
        }
        return this;
    }

    public PreferencesService setValue(String key, int value) {
        if (preferences != null && editor != null) {
            editor.putInt(key, value);
        }
        return this;
    }

    public PreferencesService setValue(String key, long value) {
        if (preferences != null && editor != null) {
            editor.putLong(key, value);
        }
        return this;
    }

    public PreferencesService setValue(String key, float value) {
        if (preferences != null && editor != null) {
            editor.putFloat(key, value);
        }
        return this;
    }

    public PreferencesService setValue(String key, boolean value) {
        if (preferences != null && editor != null) {
            editor.putBoolean(key, value);
        }
        return this;
    }

    public PreferencesService deleteKey(String key) {
        if (preferences != null && editor != null) {
            editor.remove(key);
        }
        return this;
    }

    public void apply() {
        if (preferences != null && editor != null) {
            editor.apply();
        }
    }

    private void createEditor() {
        editor = preferences.edit();
    }
}
