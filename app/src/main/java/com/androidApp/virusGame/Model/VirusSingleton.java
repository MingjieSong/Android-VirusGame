package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
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

    //return number of table
    public long getRowCount(String tablename){
        mDatabase = HomeScreenActivity.dbHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(mDatabase, tablename);
        return count;
    }

    public void addVirus(){
        if (getRowCount(DbSchema.VirusTable.NAME)!=3) {
            List<ContentValues> contentValuesList = setUpVirus();
            for (int i = 0; i < contentValuesList.size(); i++) {
                mDatabase.beginTransaction();
                try {
                    mDatabase.insert(DbSchema.VirusTable.NAME, null, contentValuesList.get(i));
                    mDatabase.setTransactionSuccessful();
                } finally {
                    mDatabase.endTransaction();
                }
            }
        }

    }

    private List<ContentValues> setUpVirus(){
        List<ContentValues>  contentValuesList = new ArrayList();
        List<Virus> virusList = new  ArrayList();
        virusList.add(new Virus("flu virus", "1","2,3")) ;
        virusList.add(new Virus("COVID 19", "3","4,4")) ;
        virusList.add(new Virus("HIV", "10","5,2")) ;

        for(int i =0 ;i<virusList.size() ; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbSchema.VirusTable.Cols.NAME, virusList.get(i).getName());
            contentValues.put(DbSchema.VirusTable.Cols.HITPOINT, virusList.get(i).getHitpt());
            contentValues.put(DbSchema.VirusTable.Cols.LOCATION, virusList.get(i).getLocation());
            contentValuesList.add(contentValues)  ;
        }

        return contentValuesList ;
    }


    private CursorWrapper queryVirus(){

        Cursor cursor = mDatabase.query(
                DbSchema.VirusTable.NAME,
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

                String name = cursor.getString(cursor.getColumnIndex(DbSchema.VirusTable.Cols.NAME));
                String hitpt = cursor.getString(cursor.getColumnIndex(DbSchema.VirusTable.Cols.HITPOINT));
                String location = cursor.getString(cursor.getColumnIndex(DbSchema.VirusTable.Cols.LOCATION));
                Virus virus = new Virus(name, hitpt,location);
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
            int htpt = cursor.getInt(cursor.getColumnIndex(DbSchema.VirusTable.Cols.HITPOINT));
            String loc = cursor.getString(cursor.getColumnIndex(DbSchema.VirusTable.Cols.LOCATION));
            Log.d("Found the virus's info", "The virus "+ name + "'s hitpoint is "+ htpt + " Location: ("+loc+")");

        }

    }
    private static ContentValues getContentValues(Virus virus) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.VirusTable.Cols.NAME, virus.getName());
        values.put(DbSchema.VirusTable.Cols.HITPOINT, virus.getHitpt());
        values.put(DbSchema.VirusTable.Cols.LOCATION, virus.getLocation());
        return values;
    }


    //update single virus's info
    public void updateSingleVirus(String name, String hitpoint, String location) {
        Virus virus = new Virus(name,hitpoint,location);
        ContentValues newContent = getContentValues(virus) ;
        String whereArgs[] = {name};
        mDatabase.update(DbSchema.VirusTable.NAME, newContent, "NAME=?", whereArgs);

    }

    public void deleteAllVirus() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(DbSchema.VirusTable.NAME, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }



}

