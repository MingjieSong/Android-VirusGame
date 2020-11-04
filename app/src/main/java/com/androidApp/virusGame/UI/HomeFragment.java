package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidApp.virusGame.Model.Player;
import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Activity activity = getActivity();

        if (activity != null){

            Button btnStart = v.findViewById(R.id.get_started_button);
            btnStart.setOnClickListener(this);
            Button btnSettings = v.findViewById(R.id.settings_button);
            btnSettings.setOnClickListener(this);
            Button btnProfile = v.findViewById(R.id.profile_button);
            btnProfile.setOnClickListener(this);
            Button btnResources = v.findViewById(R.id.resources_button);
            btnResources.setOnClickListener(this);

        }

        return v;
    }
    private boolean hasNetworkConnection() {
        Activity activity = getActivity();

        if (activity != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return (activeNetwork != null && activeNetwork.isConnected());
            }
            return false;
        } else {
            return false;
        }
    }



    private void launchWebView() {
        Activity activity = getActivity();

        if (activity != null) {
            Intent launchWebViewIntent = new Intent(getActivity().getApplicationContext(), WebActivity.class);
            launchWebViewIntent.putExtra("url", "https://coronavirus.dc.gov/?gclid=CjwKCAiAv4n9BRA9EiwA30WND7LYSdXq2fxretSMGL9156XGVQzGyFR8ST5gpb-SRtmoVcdW1YDveRoC_xcQAvD_BwE");
            startActivity(launchWebViewIntent);
        }
    }

    // 0oi1OI!

    private void noNetworkConnectionNotify() {
        Toast.makeText(this.getContext(), "Error: No network connection", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View view) {
        String username=getActivity().getIntent().getStringExtra("USER");
        FragmentActivity activity=getActivity();
        Intent intent;
        switch (view.getId()) {
            case R.id.get_started_button:
                intent =new Intent( getActivity(), MaskCheckActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_button:
                //TODO: go to settings activity
                break;
            case R.id.profile_button:
                //need information about which user is logged in
                intent =new Intent( getActivity(), ProfileActivity.class);
                intent.putExtra("USER",username);
                startActivity(intent);
                break;
            case R.id.resources_button:
                if(hasNetworkConnection()){
                    launchWebView();
                }else{
                    noNetworkConnectionNotify();

                }

        }
    }


}