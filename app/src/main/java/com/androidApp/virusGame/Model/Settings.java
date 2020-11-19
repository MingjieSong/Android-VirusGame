package com.androidApp.virusGame.Model;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Class for reading settings from SharedPreferences.
 *
 * Created by adamcchampion on 2017/08/14.
 */

public class Settings {

    private final static String OPT_NAME_DEF = "Player";
    private final static String OPT_PLAY_FIRST = "human_starts";
    private final static boolean OPT_PLAY_FIRST_DEF = true;


    public static String getName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("USER","default");
    }

    public static boolean doesHumanPlayFirst(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_PLAY_FIRST, OPT_PLAY_FIRST_DEF);
    }
}
