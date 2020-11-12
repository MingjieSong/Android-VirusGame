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
public class WinFragment extends Fragment implements View.OnClickListener{

    public WinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_win, container, false);

        Activity activity = getActivity();
        String virus=activity.getIntent().getStringExtra("virusName");

        if (activity != null){

            Button btnHome= v.findViewById(R.id.winhome);
            btnHome.setOnClickListener(this);
            TextView winTxt=(TextView)v.findViewById(R.id.winText);
            winTxt.setText("Congrats! You have successfully sanitized the "+virus+". It has been added to your collection.");

        }


        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.winhome:
                intent =new Intent( getActivity(), HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


}