package com.androidApp.virusGame.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "VirusGame.db";
        private static final int DATABASE_VERSION = 1;


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + PlayerDbSchema.PlayerTable.NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PlayerDbSchema.PlayerTable.Cols.NAME + " TEXT, " +
                    PlayerDbSchema.PlayerTable.Cols.PASSWORD + " TEXT" +
                    ")");

            sqLiteDatabase.execSQL("CREATE TABLE " + VirusDbSchema.VirusTable.NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VirusDbSchema.VirusTable.Cols.NAME + " TEXT, " +
                    VirusDbSchema.VirusTable.Cols.HITPOINT + " INTEGER " +
                    //VirusDbSchema.VirusTable.Cols.LOCATION + " TEXT " +
                    //VirusDbSchema.VirusTable.Cols.IMAGE + "BLOB" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.d("Upgrading message","Upgrading database; dropping and recreating tables.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlayerDbSchema.PlayerTable.NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VirusDbSchema.VirusTable.NAME);
            onCreate(sqLiteDatabase);
        }


    }


