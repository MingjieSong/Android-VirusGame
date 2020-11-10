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
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameFragment extends Fragment implements View.OnClickListener  {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);
        Activity activity = getActivity();
        //just for testing purposes
        final ImageButton virusButton=v.findViewById(R.id.virus);
        virusButton.setOnClickListener(this);
        startRandomButton(virusButton);
        return v;
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
        switch(view.getId()){
            //the virus gets destroyed when it's clicked
            case R.id.virus:
                view.setVisibility(View.GONE);
                break;

        }
        /* this needs to be called after a successful login
        intent=new Intent( getActivity(), MaskCheckActivity.class);
        startActivity(intent); */
        //just called here so we can trigger onDestroy
        //getActivity().finish();

    }



}