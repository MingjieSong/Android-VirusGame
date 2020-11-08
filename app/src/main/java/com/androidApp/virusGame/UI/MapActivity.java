package com.androidApp.virusGame.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapActivity extends SingleFragmentActivity  {

    private static final int REQUEST_ERROR =0;
    @Override
    protected Fragment createFragment() {

        return  new MapFragment();

    }



    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode == ConnectionResult.SUCCESS) {
            Toast.makeText(this.getApplicationContext(), "Google Play Service set!", Toast.LENGTH_SHORT).show();
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(errorCode)) {
            Toast.makeText(this.getApplicationContext(), "Fixable error",  Toast.LENGTH_SHORT).show();
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR) ;
            errorDialog.show();
        }else{
            Toast.makeText(this.getApplicationContext(), "Error connecting to google play service", Toast.LENGTH_SHORT).show();
        }
    }
}
