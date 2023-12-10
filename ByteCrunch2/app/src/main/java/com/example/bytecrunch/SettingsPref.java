package com.example.bytecrunch;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPref {

    private SharedPreferences saveSettings;
    private int themeSelection;
    private static final String KEY_OF_THEME = "key_theme";
    private static final String KEY_OF_FONT = "key_font";

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_CONTRAST = 2;
    private String fontSelection;


    /**
     * Constructor to get values from shared preference
     * @param context
     */
    public SettingsPref (Context context) {
        saveSettings = context.getSharedPreferences("APP_Settings", Context.MODE_PRIVATE);
        themeSelection = saveSettings.getInt(KEY_OF_THEME, 0);
        fontSelection = saveSettings.getString(KEY_OF_FONT, "roboto");

    }


    /**
     * Getters
     * @return selection
     */
    public int getThemeSelection() {
        return themeSelection;
    }

    public String getFontSelection() {
        return fontSelection;
    }



    /**
     * Save settings options for theme selection to shared preference
     * @param theme
     */
    public void setThemeSelection (int theme) {
        themeSelection = theme;
        SharedPreferences.Editor editor = saveSettings.edit();
        editor.putInt(KEY_OF_THEME, theme);
        editor.commit();
    }

    /**
     * Save settings options for font selection to shared preference
     * @param font
     */
    public void setFontSelection (String font) {
        fontSelection = font;
        SharedPreferences.Editor editor = saveSettings.edit();
        editor.putString(KEY_OF_FONT, font);
        editor.commit();
    }
}