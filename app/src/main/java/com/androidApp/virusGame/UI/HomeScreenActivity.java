package com.androidApp.virusGame.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;

import com.androidApp.virusGame.Model.DbHelper;
import com.androidApp.virusGame.Model.VirusSingleton;


import java.io.ByteArrayOutputStream;
import java.io.File;



public class HomeScreenActivity extends SingleFragmentActivity {
    public static DbHelper dbHelper ;
    /* File pinkVirusFile = new File("/app/src/main/pinkvirus-playstore.png");
    private String pink_virus_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
    File.separator + "pinkvirus-playstore.png"; */


    @Override
    protected HomeScreenFragment createFragment(){return new HomeScreenFragment() ;}


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getApplicationContext());
        VirusSingleton singleton = VirusSingleton.get(getApplicationContext());
        //Add viruses into the db
        singleton.addVirus();



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save data
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Get access to more stored data
    }




    //convert bitmap image into byte array
    // Convert that image to byte array & store that byte [] to DB.
    // While retrieving that image get byte [] convert that byte [] to bitmap by which you will get original image.
    public byte[] convertToByteArray(File virusFile) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(virusFile.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }




}