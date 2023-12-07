package com.example.bytecrunch;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPref {

    private SharedPreferences saveSettings;
    private int themeSelection;
    private static final String KEY_OF_THEME = "theme";
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_CONTRAST = 2;


    /**
     * Constructor
     * @param context
     */
    public SettingsPref (Context context) {
        saveSettings = context.getSharedPreferences("APP_Settings", Context.MODE_PRIVATE);
        themeSelection = saveSettings.getInt(KEY_OF_THEME, 0);

    }


    /**
     * Getter
     * @return int Theme selection
     */
    public int getThemeSelection() {
        return themeSelection;
    }

    /**
     * Save settings options to shared preference
     * @param theme
     */
    public void setThemeSelection (int theme) {
        themeSelection = theme;
        SharedPreferences.Editor editor = saveSettings.edit();
        editor.putInt(KEY_OF_THEME, theme);
        editor.commit();
    }
}