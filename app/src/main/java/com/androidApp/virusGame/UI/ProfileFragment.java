package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidApp.virusGame.Model.Player;
import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Activity activity = getActivity();
        String username=activity.getIntent().getStringExtra("USER");

        if (activity != null){

            Button btnFB = v.findViewById(R.id.post_to_FB_button);
            btnFB.setOnClickListener(this);
            Button btnHome= v.findViewById(R.id.go_home_button);
            btnHome.setOnClickListener(this);
            TextView welcomeTxt=(TextView)v.findViewById(R.id.welcome_user);
            welcomeTxt.setText("Welcome "+username);

        }


        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentActivity activity=getActivity();
        Intent intent;
        switch (view.getId()) {
            case R.id.post_to_FB_button:
                //TODO: add facebook functionality
                break;
            case R.id.go_home_button:
                intent =new Intent( getActivity(), HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


}