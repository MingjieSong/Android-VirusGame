//password encryption done with the help of https://www.youtube.com/watch?v=kN8hlHO8US0&ab_channel=SylvainSaurel

package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.androidApp.virusGame.UI.HomeScreenActivity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class PlayerSingleton {
    private static final String encryptionKey="ldsfbkbfaskbfojsbdfka310875408w70513uqbsdkdjbjfkdsbfb0138750328y52";
    private static PlayerSingleton sPlayer;

    private SQLiteDatabase mDatabase;

    private static final String INSERT_STMT = "INSERT INTO " + DbSchema.PlayerTable.NAME + " (name, password) VALUES (?, ?)" ;

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
    public boolean addPlayer(Player player) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if(!checkUsernameDuplicate(player.getName())) {
            ContentValues contentValues = getContentValues(player);
            mDatabase.beginTransaction();
            try {
                SQLiteStatement statement = mDatabase.compileStatement(INSERT_STMT);
                statement.bindString(1, contentValues.getAsString(DbSchema.PlayerTable.Cols.NAME));
                statement.bindString(2, contentValues.getAsString(DbSchema.PlayerTable.Cols.PASSWORD));
                statement.executeInsert();
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();

            }
            return true ;
        }
        return false ;
    }

//FIXME: Add update virus method to player's stored vuris

    //Delete all players info from the database.
    public void deleteAllPlayers() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(DbSchema.PlayerTable.NAME, null, null);
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

                String name = cursor.getString(cursor.getColumnIndex(DbSchema.PlayerTable.Cols.NAME));
                String password = cursor.getString(cursor.getColumnIndex(DbSchema.PlayerTable.Cols.PASSWORD));
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
            String password = cursor.getString(cursor.getColumnIndex(DbSchema.PlayerTable.Cols.PASSWORD));
            Log.d("Found the player's info", "The player "+ username + "'s password is "+ password);
            //FIXME: show the retrieved player's info on the screen

        }

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



    //delete single player's info from db
    public void deleteSinglePlayerByName( String name ) {
        String whereClause = "NAME=?";
        String whereArgs[] = {name};
        mDatabase.delete(DbSchema.PlayerTable.NAME , whereClause, whereArgs) ;
    }


    private static ContentValues getContentValues(Player player) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ContentValues values = new ContentValues();
        values.put(DbSchema.PlayerTable.Cols.NAME, player.getName());
        String encrypted=encrypt(player.getPassword());
        values.put(DbSchema.PlayerTable.Cols.PASSWORD, encrypted);
        Log.d("debug", "encrypted password");
        return values;
    }

    private CursorWrapper queryPlayer() {
        Cursor cursor = mDatabase.query(
                DbSchema.PlayerTable.NAME,
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
                String name = cursor.getString(cursor.getColumnIndex(DbSchema.PlayerTable.Cols.NAME));
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
                //decrypt the password stored in the database
                String decrypted=decrypt(cursor.getString(2));
                Log.d("debug", "decrypted password: "+decrypted);
                //check password
                if(decrypted.equals(password)){
                    //login successful
                    return 0;
                }else{
                    return 2;
                    //incorrect password
                }
            }
            mDatabase.setTransactionSuccessful();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
        }

        return -1;
    }


    //below are methods for relational db between player and virus
    public void addVirusToPlayer(String player, String virus) {
        String[]where=new String[]{player};
        Cursor cursorForPlayer=mDatabase.rawQuery("SELECT * from players WHERE name=?",where);
        where=new String[]{virus};
        Cursor cursorForVirus=mDatabase.rawQuery("SELECT * from virus  WHERE name=?",where);

        int playerId=0 ;
        int virusId=0 ;
        if(cursorForPlayer== null ||cursorForPlayer.getCount()<=0){
            Log.d("error", "player not found");

        }else if(cursorForPlayer!=null) {
            cursorForPlayer.moveToFirst();
            playerId = cursorForPlayer.getInt(cursorForPlayer.getColumnIndex(DbSchema.PlayerTable.Cols.ID));
        }


        if(cursorForVirus== null ||cursorForVirus.getCount()<=0){
            Log.d("error", "virus not found");
        }else if(cursorForVirus!=null) {
            cursorForVirus.moveToFirst();
            virusId = cursorForVirus.getInt(cursorForVirus.getColumnIndex(DbSchema.VirusTable.Cols.ID));
        }


        ContentValues values = new ContentValues();
        values.put(DbSchema.CaughtVirus.Cols.PLAYER_ID,  playerId) ;
        values.put(DbSchema.CaughtVirus.Cols.VIRUS_ID,  virusId);

        mDatabase.beginTransaction();
        try {
            mDatabase.insert(DbSchema.CaughtVirus.NAME, null, values);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    private CursorWrapper queryPlayerVirus() {
        Cursor cursor = mDatabase.query(
                DbSchema.CaughtVirus.NAME,
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
    public List<Pair<String, String>> getPlayerAndVirus() {
        List<Pair<String, String>> player_virus_List = new ArrayList<>();
        try (CursorWrapper cursor = queryPlayerVirus()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int player_id = cursor.getInt(cursor.getColumnIndex(DbSchema.CaughtVirus.Cols.PLAYER_ID));
                int virus_id = cursor.getInt(cursor.getColumnIndex(DbSchema.CaughtVirus.Cols.VIRUS_ID));


                String[]where=new String[]{String.valueOf(player_id)};
                Cursor cursorForPlayer=mDatabase.rawQuery("SELECT name from players WHERE id=?",where);

                where=new String[]{String.valueOf(virus_id)};
                Cursor cursorForVirus=mDatabase.rawQuery("SELECT name from virus WHERE id=?",where);
                cursorForPlayer.moveToFirst();
                cursorForVirus.moveToFirst();
                player_virus_List.add(new Pair<String, String>(cursorForPlayer.getString(0), cursorForVirus.getString(0)));
                cursor.moveToNext();
            }
        }
        return player_virus_List;
    }

    private static String encrypt(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key=generateKey();
        Cipher c= Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[]encVal=c.doFinal(password.getBytes());
        String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private static SecretKeySpec generateKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
       final MessageDigest digest= MessageDigest.getInstance("SHA-256");
       byte[]bytes = encryptionKey.getBytes("UTF-8");
       digest.update(bytes,0,bytes.length);
       byte[]key=digest.digest();
       SecretKeySpec skspc=new SecretKeySpec(key,"AES");
       return skspc;

    }

    private static String decrypt(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skspc=generateKey();
        Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,skspc);
        byte[]decodedValue=Base64.decode(password,Base64.DEFAULT);
        byte[]decValue=c.doFinal(decodedValue);
        String decryptedValue=new String(decValue);
        return decryptedValue;

    }




}
