package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidApp.virusGame.UI.HomeScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class VirusSingleton  {
    private static VirusSingleton sVirus;

    private SQLiteDatabase mDatabase;


    //construct the only one PlayerSingleton object here
    public static VirusSingleton get() {
        if (sVirus == null) {
            sVirus = new VirusSingleton();
        }
        return sVirus;
    }

    //private constructor
    private VirusSingleton() {
        //get db
        mDatabase =  HomeScreenActivity.dbHelper.getWritableDatabase();
    }


    public void addVirus(){

        List<ContentValues> contentValuesList = setUpVirus() ;
        for(int i= 0 ; i<contentValuesList.size() ; i++) {
            mDatabase .beginTransaction();
            try {
                mDatabase .insert(VirusDbSchema.VirusTable.NAME, null, contentValuesList.get(i));
                mDatabase .setTransactionSuccessful();
            } finally {
                mDatabase .endTransaction();
            }
        }

    }

    private List<ContentValues> setUpVirus(){
        List<ContentValues>  contentValuesList = new ArrayList();
        List<Virus> virusList = new  ArrayList();
        virusList.add(new Virus("flu virus", "1")) ;
        virusList.add(new Virus("COVID 19", "3")) ;
        virusList.add(new Virus("HIV", "10")) ;

        for(int i =0 ;i<virusList.size() ; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(VirusDbSchema.VirusTable.Cols.NAME, virusList.get(i).getName());
            contentValues.put(VirusDbSchema.VirusTable.Cols.HITPOINT, virusList.get(i).getHitpt());
            contentValuesList.add(contentValues)  ;
        }

        return contentValuesList ;
    }

    private CursorWrapper queryVirus(){

        Cursor cursor = mDatabase.query(
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

