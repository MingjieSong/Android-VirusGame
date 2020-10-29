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


}