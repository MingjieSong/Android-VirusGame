package com.androidApp.virusGame;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class HomeScreenFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Debugging message";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState) ;
        Log.d(TAG, "onCreate()") ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        Log.d(TAG, "onCreateView()") ;
      // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homescreen, container, false);
    }


   //FIXME: add more @Override methods if we choose to use fragment






    //FIXME: add switch statement based on the layout
    @Override
    public void onClick(View view) {

    }
}
