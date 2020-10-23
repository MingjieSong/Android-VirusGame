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
import java.util.Vector;

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
            mDatabase.beginTransaction();
            try {
                mDatabase.insert(VirusDbSchema.VirusTable.NAME, null, contentValuesList.get(i));
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    private List<ContentValues> setUpVirus(){
        List<ContentValues>  contentValuesList = new ArrayList();
        List<Virus> virusList = new  ArrayList();
        virusList.add(new Virus("flu virus", 1)) ;
        virusList.add(new Virus("COVID 19", 3)) ;
        virusList.add(new Virus("HIV", 10)) ;

        for(int i =0 ;i<virusList.size() ; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(VirusDbSchema.VirusTable.Cols.NAME, virusList.get(i).getName());
            contentValues.put(VirusDbSchema.VirusTable.Cols.HITPOINT, virusList.get(i).getHitpt());
            //contentValues.put(VirusDbSchema.VirusTable.Cols.LOCATION, virusList.get(i).getLocation());
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
                int hitpt = cursor.getInt(cursor.getColumnIndex(VirusDbSchema.VirusTable.Cols.HITPOINT));
                //String location = cursor.getString(cursor.getColumnIndex(VirusDbSchema.VirusTable.Cols.LOCATION));
                Virus virus = new Virus(name, hitpt);
                virusList.add(virus);
                cursor.moveToNext();
            }
        }

        return virusList;
    }

    //retrieve single virus's info by its name
    public void getSingleVirus(String name) {
        String[]where=new String[]{name};
        Cursor cursor=mDatabase.rawQuery("SELECT * from virus WHERE name=?",where);
        if(cursor== null ||cursor.getCount()<=0){
            Log.d("error", "virus not found");

        }else if(cursor!=null) {
            cursor.moveToFirst() ;
            int htpt = cursor.getInt(cursor.getColumnIndex(VirusDbSchema.VirusTable.Cols.HITPOINT));
            Log.d("Found the virus's info", "The virus "+ name + "'s hitpoint is "+ htpt);

        }

    }
    private static ContentValues getContentValues(Virus virus) {
        ContentValues values = new ContentValues();
        values.put(VirusDbSchema.VirusTable.Cols.NAME, virus.getName());
        values.put(VirusDbSchema.VirusTable.Cols.NAME, virus.getHitpt());

        return values;
    }


    //update single virus's hitpoint
    public void updateSingleVirusHitpoint(String name, int hitpoint) {
        Virus virus = new Virus(name,hitpoint);
        ContentValues newContent = getContentValues(virus) ;
        String whereClause = "NAME=?";
        String whereArgs[] = {virus.getName()};
        mDatabase.update(VirusDbSchema.VirusTable.NAME, newContent, whereClause, whereArgs);

    }

    public void deleteAllVirus() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(VirusDbSchema.VirusTable.NAME, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }



}

