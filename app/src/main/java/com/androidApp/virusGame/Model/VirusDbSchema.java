package com.androidApp.virusGame.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//virus schema
public class VirusDbSchema {
    static final class VirusTable{
        static final String NAME = "virus";

        static final class Cols {
            static final String NAME = "name";
            static final String HITPOINT = "hitpoint";

        }
    }
}