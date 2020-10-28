package com.androidApp.virusGame.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "VirusGame.db";
        private static final int DATABASE_VERSION = 1;
        private static final String PLAYER_TABLE = DbSchema.PlayerTable.NAME;
        private static final String PLAYER_NAME = DbSchema.PlayerTable.Cols.NAME;
        private static final String PLAYER_PSD = DbSchema.PlayerTable.Cols.PASSWORD;

        private static final String VIRUS_TABLE = DbSchema.VirusTable.NAME;
        private static final String VIRUS_NAME = DbSchema.VirusTable.Cols.NAME ;
        private static final String VIRUS_HP = DbSchema.VirusTable.Cols.HITPOINT;
        private static final String VIRUS_LOCATION = DbSchema.VirusTable.Cols.LOCATION;

        private static final String CAUGHTVIRUS_TABLE = DbSchema.CaughtVirus.NAME;
        private static final String PLAYER_ID = DbSchema.CaughtVirus.Cols.PLAYER_ID;
        private static final String VIRUS_ID = DbSchema.CaughtVirus.Cols.VIRUS_ID;


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + PLAYER_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLAYER_NAME + " TEXT, " +
                    PLAYER_PSD + " TEXT " +")");

            sqLiteDatabase.execSQL("CREATE TABLE " + VIRUS_TABLE+ "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VIRUS_NAME + " TEXT, " +
                    VIRUS_HP + " TEXT, " +
                    VIRUS_LOCATION + " TEXT " +
                    //VirusDbSchema.VirusTable.Cols.IMAGE + "BLOB" +
                    ")");

            sqLiteDatabase.execSQL("CREATE TABLE "+CAUGHTVIRUS_TABLE+" ("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLAYER_ID + " INTEGER," +
                    VIRUS_ID + " INTEGER, " +
                    "FOREIGN KEY (" + PLAYER_ID + ") REFERENCES " + PLAYER_TABLE + "(" + "_id" + ") ON DELETE CASCADE,"+
                    "FOREIGN KEY (" + VIRUS_ID + ") REFERENCES " + VIRUS_TABLE + "(" + "_id" + ") ON DELETE CASCADE);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.d("Upgrading message","Upgrading database; dropping and recreating tables.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIRUS_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAUGHTVIRUS_TABLE);
            onCreate(sqLiteDatabase);
        }


    }


