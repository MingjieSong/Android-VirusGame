package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/*this class uses code from https://stackoverflow.com/questions/32263233/how-to-make-a-button-randomly-move*/


public class GameFragment extends Fragment implements View.OnClickListener  {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);
        Activity activity = getActivity();
        String currentVirus=activity.getIntent().getStringExtra("virusName");
        //just for testing purposes
        //fill a list with buttons
        final ImageButton one;
        final ImageButton two;
        final ImageButton three;
        final ImageButton four;

        switch(currentVirus){
            case "fluvirus":
                one=v.findViewById(R.id.fluvirus1);
                two=v.findViewById(R.id.fluvirus2);
                three=v.findViewById(R.id.fluvirus3);
                four=v.findViewById(R.id.fluvirus4);
                break;
           case "coronavirus":
                one=v.findViewById(R.id.coronavirus1);
                two=v.findViewById(R.id.coronavirus2);
                three=v.findViewById(R.id.coronavirus3);
                four=v.findViewById(R.id.coronavirus4);
                break;
            default:
                one=v.findViewById(R.id.hiv1);
                two=v.findViewById(R.id.hiv2);
                three=v.findViewById(R.id.hiv3);
                four=v.findViewById(R.id.hiv4);
                break;

        }
        setOthersNonVisible(currentVirus, v);
        one.setOnClickListener(this);
        startRandomButton(one);

        two.setOnClickListener(this);
        startRandomButton(two);

        three.setOnClickListener(this);
        startRandomButton(three);

        four.setOnClickListener(this);
        startRandomButton(four);
        return v;
    }

    public static void setOthersNonVisible(String virusName, View v){
        switch(virusName) {
            case "fluvirus":
                v.findViewById(R.id.coronavirus1).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus2).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus3).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus4).setVisibility(View.GONE);
                v.findViewById(R.id.hiv1).setVisibility(View.GONE);
                v.findViewById(R.id.hiv2).setVisibility(View.GONE);
                v.findViewById(R.id.hiv3).setVisibility(View.GONE);
                v.findViewById(R.id.hiv4).setVisibility(View.GONE);
                break;
            case "coronavirus":
                v.findViewById(R.id.fluvirus1).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus2).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus3).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus4).setVisibility(View.GONE);
                v.findViewById(R.id.hiv1).setVisibility(View.GONE);
                v.findViewById(R.id.hiv2).setVisibility(View.GONE);
                v.findViewById(R.id.hiv3).setVisibility(View.GONE);
                v.findViewById(R.id.hiv4).setVisibility(View.GONE);
                break;
            default:
                v.findViewById(R.id.fluvirus1).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus2).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus3).setVisibility(View.GONE);
                v.findViewById(R.id.fluvirus4).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus1).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus2).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus3).setVisibility(View.GONE);
                v.findViewById(R.id.coronavirus4).setVisibility(View.GONE);
                break;
        }
    }
    public static Point getDisplaySize(@NonNull Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    private void setButtonRandomPosition(ImageButton button){
        int randomX = new Random().nextInt(getDisplaySize(getContext()).x);
        int randomY = new Random().nextInt(getDisplaySize(getContext()).y);
        button.setX(randomX);
        button.setY(randomY);
    }
    private void startRandomButton(final ImageButton button) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setButtonRandomPosition(button);
            }
        }, 0, 1000);//Update button every second
    }

    @Override
    public void onClick(View view) {
        Intent intent ;
        view.setVisibility(View.GONE);

    }



}