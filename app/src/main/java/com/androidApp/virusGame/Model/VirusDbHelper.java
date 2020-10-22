package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

public class VirusDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "VirusGame.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "virusdata";

    //virus db constructor
    public VirusDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VirusDbSchema.VirusTable.NAME + "(" +
              "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VirusDbSchema.VirusTable.Cols.NAME + " TEXT, " +
                VirusDbSchema.VirusTable.Cols.HITPOINT + "INTEGER" +
                VirusDbSchema.VirusTable.Cols.IMAGE + "BLOB" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("Upgrading message","Upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VirusDbSchema.VirusTable.NAME);
        onCreate(sqLiteDatabase);
    }


    public void insertVirus(String name, int hitpt, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VirusDbSchema.VirusTable.Cols.NAME, name);
        contentValues.put(VirusDbSchema.VirusTable.Cols.HITPOINT, hitpt);
        contentValues.put(VirusDbSchema.VirusTable.Cols.IMAGE, image);
        db.insert(VirusDbSchema.VirusTable.NAME, null, contentValues);
    }


}

