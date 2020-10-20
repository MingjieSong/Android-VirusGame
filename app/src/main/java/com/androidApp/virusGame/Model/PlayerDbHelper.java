package com.androidApp.virusGame.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class PlayerDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "VirusGame.db";
        private static final int DATABASE_VERSION = 1;


        public PlayerDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + PlayerDbSchema.PlayerTable.NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PlayerDbSchema.PlayerTable.Cols.NAME + " TEXT, " +
                    PlayerDbSchema.PlayerTable.Cols.PASSWORD + " TEXT" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.d("Upgrading message","Upgrading database; dropping and recreating tables.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlayerDbSchema.PlayerTable.NAME);
            onCreate(sqLiteDatabase);
        }


    }


