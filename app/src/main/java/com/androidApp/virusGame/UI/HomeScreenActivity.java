package com.androidApp.virusGame.UI;

import android.os.Bundle;



public class HomeScreenActivity extends SingleFragmentActivity {




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