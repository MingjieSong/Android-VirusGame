package com.androidApp.virusGame.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.androidApp.virusGame.Model.VirusDbHelper;
import com.androidApp.virusGame.Model.VirusDbSchema;
import com.androidApp.virusGame.R;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class HomeScreenActivity extends SingleFragmentActivity {
    VirusDbHelper virusDb;
    File pinkVirusFile = new File("/app/src/main/pinkvirus-playstore.png");
    //private String pink_virus_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
    //File.separator + "pinkvirus-playstore.png";


    @Override
    protected HomeScreenFragment createFragment(){return new HomeScreenFragment() ;}


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        virusDb = new VirusDbHelper(this);

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