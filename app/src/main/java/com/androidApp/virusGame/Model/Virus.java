package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;

public class Virus {
    private String NAME;
    private String HITPOINT;


    public Virus(String name, String hitpt) {
        NAME = name;
        HITPOINT = hitpt;
    }

    public String getName() {
        return NAME;
    }

    public String getHitpt() {
        return HITPOINT;
    }

}