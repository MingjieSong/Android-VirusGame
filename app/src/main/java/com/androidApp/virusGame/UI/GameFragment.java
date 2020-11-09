package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidApp.virusGame.Model.Player;
import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.Model.Virus;
import com.androidApp.virusGame.Model.VirusSingleton;
import com.androidApp.virusGame.R;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.List;



public class GameFragment extends Fragment   {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);
        Activity activity = getActivity();
        if(activity!=null) {
            String virusType = activity.getIntent().getStringExtra("virusName");
            Toast.makeText( activity,  virusType+ " is coming !", Toast.LENGTH_SHORT).show();
        }

        return v;
    }



}