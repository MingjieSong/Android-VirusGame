package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidApp.virusGame.UI.HomeScreenActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VirusSingleton  {
    private static VirusSingleton sVirus;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private int virusCount = 3 ;

    //construct the only one VirusSingleton object here
    public static VirusSingleton get(Context context) {
        if (sVirus == null) {
            sVirus = new VirusSingleton(context);
        }
        return sVirus;
    }

    //private constructor
    private VirusSingleton(Context context) {
        //get db
        mDatabase =  HomeScreenActivity.dbHelper.getWritableDatabase();
        mContext = context;
    }

    //return number of table
    public long getRowCount(String tablename){
        mDatabase = HomeScreenActivity.dbHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(mDatabase, tablename);
        return count;
    }

    public void addVirus(){
        if (getRowCount(DbSchema.VirusTable.NAME)!=virusCount) {
            List<ContentValues> contentValuesList = null;
            try {
                contentValuesList = setUpVirus();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private List<ContentValues> setUpVirus() throws IOException {
        List<ContentValues>  contentValuesList = new ArrayList<>();
        List<Virus> virusList = new  ArrayList<>();



        virusList.add(new Virus("flu virus", "1","2,3", getBytesByPath("fluvirus.png"))) ;
        virusList.add(new Virus("COVID 19", "3","4,4",getBytesByPath("coronavirus.png"))) ;
        virusList.add(new Virus("HIV", "10","5,2",getBytesByPath("hivvirus.png"))) ;

        for(int i =0 ;i<virusList.size() ; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbSchema.VirusTable.Cols.NAME, virusList.get(i).getName());
            contentValues.put(DbSchema.VirusTable.Cols.HITPOINT, virusList.get(i).getHitpt());
            contentValues.put(DbSchema.VirusTable.Cols.LOCATION, virusList.get(i).getLocation());
            contentValues.put(DbSchema.VirusTable.Cols.IMAGE, virusList.get(i).getImage()); //change image byte to image path upper bounds for image size

            contentValuesList.add(contentValues)  ;
        }

        return contentValuesList ;
    }


    //convert from path name to bitmap then to byte array
    public byte[] getBytesByPath(String path) throws IOException {
        AssetManager am = mContext.getAssets();
        InputStream inputstream = am.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    //convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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
                byte[] image = cursor.getBlob(cursor.getColumnIndex(DbSchema.VirusTable.Cols.IMAGE));
                Virus virus = new Virus(name, hitpt,location, image);
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
            byte[] img = cursor.getBlob(cursor.getColumnIndex(DbSchema.VirusTable.Cols.IMAGE));
            Log.d("Found the virus's info", "The virus "+ name + "'s hitpoint is "+ htpt + " Location: ("+loc+")" +" Image byte array: "+ img);

        }

    }
    private static ContentValues getContentValues(Virus virus) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.VirusTable.Cols.NAME, virus.getName());
        values.put(DbSchema.VirusTable.Cols.HITPOINT, virus.getHitpt());
        values.put(DbSchema.VirusTable.Cols.LOCATION, virus.getLocation());
        values.put(DbSchema.VirusTable.Cols.IMAGE, virus.getImage());
        return values;
    }


    //update single virus's info
    public void updateSingleVirus(String name, String hitpoint, String location, byte[] image) {
        Virus virus = new Virus(name,hitpoint,location,image);
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

