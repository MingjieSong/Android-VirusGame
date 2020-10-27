package com.androidApp.virusGame.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "VirusGame.db";
        private static final int DATABASE_VERSION = 1;
        private static final String PLAYER_TABLE = PlayerDbSchema.PlayerTable.NAME;
        private static final String PLAYER_NAME = PlayerDbSchema.PlayerTable.Cols.NAME;
        private static final String PLAYER_PSD = PlayerDbSchema.PlayerTable.Cols.PASSWORD;

    private static final String VIRUS_TABLE = VirusDbSchema.VirusTable.NAME;
    private static final String VIRUS_NAME = VirusDbSchema.VirusTable.Cols.NAME ;
    private static final String VIRUS_HP = VirusDbSchema.VirusTable.Cols.HITPOINT;


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + PLAYER_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLAYER_NAME + " TEXT, " +
                    PLAYER_PSD + " TEXT, "
                   + VIRUS_NAME + " TEXT, " + "FOREIGN KEY ("+VIRUS_NAME+") REFERENCES "+VIRUS_TABLE+"("+VIRUS_NAME+"))");


            sqLiteDatabase.execSQL("CREATE TABLE " + VIRUS_TABLE+ "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VIRUS_NAME + " TEXT, " +
                    VIRUS_HP + " TEXT " +

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


