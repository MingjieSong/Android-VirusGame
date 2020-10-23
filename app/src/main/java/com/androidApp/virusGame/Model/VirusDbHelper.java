package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class VirusDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "VirusGame.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = VirusDbSchema.VirusTable.NAME;
    private static final String VIRUS_NAME = VirusDbSchema.VirusTable.Cols.NAME;
    private static final String VIRUS_HITPOINT = VirusDbSchema.VirusTable.Cols.HITPOINT;

    //virus db constructor
    public VirusDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
              "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VIRUS_NAME + " TEXT, " +
                VIRUS_HITPOINT + "INTEGER" +
                //VirusDbSchema.VirusTable.Cols.IMAGE + "BLOB" +
                ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("Upgrading message","Upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VirusDbSchema.VirusTable.NAME);
        onCreate(sqLiteDatabase);
    }

    public void addVirus(Virus virus){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(VirusDbSchema.VirusTable.Cols.NAME, virus.getName());
        contentValues.put(VirusDbSchema.VirusTable.Cols.HITPOINT, virus.getHitpt());

        db.insert(VirusDbSchema.VirusTable.NAME,null,contentValues);
        db.close();

    }

    private CursorWrapper queryVirus(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                VirusDbSchema.VirusTable.NAME,
                null, // columns; null selects all columns
                null,
                null,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );
        return new CursorWrapper(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Virus> getVirus() {
        List<Virus> virusList = new ArrayList<>();

        try ( CursorWrapper cursor = queryVirus()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String name = cursor.getString(cursor.getColumnIndex(VirusDbSchema.VirusTable.Cols.NAME));
                String hitpt = cursor.getString(cursor.getColumnIndex(VirusDbSchema.VirusTable.Cols.HITPOINT));
                Virus virus = new Virus(name, hitpt);
                virusList.add(virus);
                cursor.moveToNext();
            }
        }

        return virusList;
    }

}

