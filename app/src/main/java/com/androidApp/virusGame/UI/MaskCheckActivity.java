package com.androidApp.virusGame.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.androidApp.virusGame.R;

public class MaskCheckActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mask_check);

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