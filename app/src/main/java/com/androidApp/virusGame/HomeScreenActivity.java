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

/* FIXME: Override activity lifecycle methods for checkpoint3, but if we choose to use fragment for future
    development(which the professor recommends us to do), we don't need most of the code below.
 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Get access to some stored data
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()") ;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()") ;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()") ;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()") ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()") ;
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