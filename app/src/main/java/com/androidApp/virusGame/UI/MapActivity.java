package com.androidApp.virusGame.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapActivity extends SingleFragmentActivity  {

    @Override
    protected Fragment createFragment() {
        return new MapFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Toast.makeText(this.getApplicationContext(), "Error, Google Play Service is not available", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.getApplicationContext(), "Google Play Service set!", Toast.LENGTH_SHORT).show();
        }
    }
}
