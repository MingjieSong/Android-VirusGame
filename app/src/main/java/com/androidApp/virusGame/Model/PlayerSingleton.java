package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidApp.virusGame.UI.HomeScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerSingleton {

    private static PlayerSingleton sPlayer;

    private SQLiteDatabase mDatabase;

    private static final String INSERT_STMT = "INSERT INTO " + PlayerDbSchema.PlayerTable.NAME + " (name, password) VALUES (?, ?)" ;

    //construct the only one PlayerSingleton object here
    public static PlayerSingleton get() {
        if (sPlayer == null) {
            sPlayer = new PlayerSingleton();
        }
        return sPlayer;
    }

    //private constructor
    private PlayerSingleton() {
        //get db
        mDatabase =  HomeScreenActivity.dbHelper.getWritableDatabase();
    }


    //Add new player info into the db
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean addPlayer(Player player) {
        if(!checkUsernameDuplicate(player.getName())) {
            ContentValues contentValues = getContentValues(player);

            mDatabase.beginTransaction();
            try {
                SQLiteStatement statement = mDatabase.compileStatement(INSERT_STMT);
                statement.bindString(1, contentValues.getAsString(PlayerDbSchema.PlayerTable.Cols.NAME));
                statement.bindString(2, contentValues.getAsString(PlayerDbSchema.PlayerTable.Cols.PASSWORD));
                statement.executeInsert();
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();

            }
            return true ;
        }
        return false ;
    }


    //Delete all players info from the database.
    public void deleteAllPlayers() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(PlayerDbSchema.PlayerTable.NAME, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }


    // Get all currently stored players' info from the db
    // It will be used to check whether the players' login info is correct
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();

        try ( CursorWrapper cursor = queryPlayer()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String name = cursor.getString(cursor.getColumnIndex(PlayerDbSchema.PlayerTable.Cols.NAME));
                String password = cursor.getString(cursor.getColumnIndex(PlayerDbSchema.PlayerTable.Cols.PASSWORD));
                Player player = new Player(name, password);
                playerList.add(player);
                cursor.moveToNext();
            }
        }

        return playerList;
    }

    //retrieve single player's info by its username
    public void getSinglePlayer(String username) {
        String[]where=new String[]{username};
        Cursor cursor=mDatabase.rawQuery("SELECT * from players WHERE name=?",where);
        if(cursor== null ||cursor.getCount()<=0){
            Log.d("error", "player not found");

        }else if(cursor!=null) {
            cursor.moveToFirst() ;
            String password = cursor.getString(cursor.getColumnIndex(PlayerDbSchema.PlayerTable.Cols.PASSWORD));
            Log.d("Found the player's info", "The player "+ username + "'s password is "+ password);
            //FIXME: show the retrieved player's info on the screen

        }

    }

    //update single player's password
    public void updateSinglePlayerPassword( String updatePassword, String name ) {
        Player player = new Player(name, updatePassword) ;
        ContentValues newContent = getContentValues(player) ;
        String whereClause = "NAME=?";
        String whereArgs[] = {player.getName()};
        mDatabase.update(PlayerDbSchema.PlayerTable.NAME, newContent, whereClause, whereArgs);

    }

    //delete single player's info from db
    public void deleteSinglePlayerByName( String name ) {
        String whereClause = "NAME=?";
        String whereArgs[] = {name};
        mDatabase.delete(PlayerDbSchema.PlayerTable.NAME , whereClause, whereArgs) ;
    }


    private static ContentValues getContentValues(Player player) {
        ContentValues values = new ContentValues();
        values.put(PlayerDbSchema.PlayerTable.Cols.NAME, player.getName());
        values.put(PlayerDbSchema.PlayerTable.Cols.PASSWORD, player.getPassword());

        return values;
    }

    private CursorWrapper queryPlayer() {
        Cursor cursor = mDatabase.query(
                PlayerDbSchema.PlayerTable.NAME,
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
    private boolean checkUsernameDuplicate(String userName) {
        try ( CursorWrapper cursor = queryPlayer()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(PlayerDbSchema.PlayerTable.Cols.NAME));
                if(userName.equals(name)){
                    return true ;
                }
                cursor.moveToNext();
            }
        }
        return false ;
    }

    public int checkLoginCredentials(String username, String password){
        Cursor cursor;
        mDatabase.beginTransaction();
        String[]where=new String[]{username};
        try {

            cursor=mDatabase.rawQuery("SELECT * from players WHERE name=?",where);
            cursor.moveToFirst();
            if(cursor== null ||cursor.getCount()<=0){
                //PLAYER not found
                return 1;
            }else if(cursor!=null){
                cursor.moveToFirst();
                //check password
                if(cursor.getString(2).equals(password)){
                    //login successful
                    return 0;
                }else{
                    return 2;
                    //incorrect password
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        return -1;
    }
    public int changePassword(String username,String newPassword){
        Cursor cursor;
        mDatabase.beginTransaction();
        String[]where=new String[]{newPassword,username};
        try {

            cursor=mDatabase.rawQuery("UPDATE players SET password=? WHERE name=?",where);
            cursor.moveToFirst();
            mDatabase.setTransactionSuccessful();
            return 0;
        } finally {
            mDatabase.endTransaction();
        }

    }





}
