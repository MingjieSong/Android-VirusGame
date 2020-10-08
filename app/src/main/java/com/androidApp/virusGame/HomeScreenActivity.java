package com.androidApp.virusGame;

import android.os.Bundle;
import android.util.Log;

/* FIXME: if we decide to have more than one fragments in HomeScreenActivity,
          HomeScreenActivity shouldn't extend SingleFragmentActivity and we need to change
          onCreate() to add several fragments and define an activity_homescreen.xml file.
 */
public class HomeScreenActivity extends SingleFragmentActivity {


    private static final String TAG = "Debugging message";

    @Override
    protected HomeScreenFragment createFragment(){return new HomeScreenFragment() ;}





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