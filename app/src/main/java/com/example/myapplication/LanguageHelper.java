package com.example.myapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import java.util.Locale;

public class LanguageHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
/*
    public static void onCreate(Context context) {

        String lang;
        if(getLanguage(context).isEmpty()){
            lang = getPersistedData(context, Locale.getDefault().getLanguage());
        }else {
            lang = getLanguage(context);
        }

        setLocale(context, lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        setLocale(context, lang);
    }
*/
    //returns current language
    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    //sets language to parameter "language", e.g. "sv" or "en"
    public static void setLocale(Context context, String language) {
        persist(context, language);
        updateResources(context, language);
    }

    //Used by getLanguage
    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    //Used by setLocale
    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    //Used by setLocale
    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }
}
