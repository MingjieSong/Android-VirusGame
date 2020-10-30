package com.androidApp.virusGame.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaskCheckFragment extends Fragment {
    private static final String TAG = "DebuggingMessage";


    public MaskCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mask_check, container, false);
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

}