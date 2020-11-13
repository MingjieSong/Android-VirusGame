package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.TextView;
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
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/*this class uses code from https://stackoverflow.com/questions/32263233/how-to-make-a-button-randomly-move*/


public class GameFragment extends Fragment{
    Timer timer;
    TimerTask task;
    CountDownTimer ct;
    int score=0;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);
        Activity activity = getActivity();
        final String username=activity.getIntent().getStringExtra("USER");
        final PlayerSingleton player = PlayerSingleton.get();
        player.getSinglePlayer(username) ;
        final String currentVirus=activity.getIntent().getStringExtra("virusName");
        final TextView scoreTxt=(TextView)v.findViewById(R.id.score);
        scoreTxt.setText("Score: "+Integer.toString(score));
        final TextView timerTxt=v.findViewById(R.id.timer);
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
        one.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                v.setVisibility(View.GONE);
                score++;
                //add killed virus to current player into relational db
                player.addVirusToPlayer(username,currentVirus);
                if(score==4){
                    stopTimer();
                    ct.cancel();
                    intent =new Intent( getActivity(), WinActivity.class);
                    intent.putExtra("USER",username);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    startActivity(intent);
                }
                scoreTxt.setText("Score: "+Integer.toString(score));
            }
        });
        two.setOnClickListener(new View.OnClickListener(){
            Intent intent;
            public void onClick(View v){
                v.setVisibility(View.GONE);
                score++;
                player.addVirusToPlayer(username,currentVirus);
                if(score==4){
                    stopTimer();
                    ct.cancel();
                    intent =new Intent( getActivity(), WinActivity.class);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    intent.putExtra("USER",username);
                    startActivity(intent);
                }
                scoreTxt.setText("Score: "+Integer.toString(score));
            }
        });
        three.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                v.setVisibility(View.GONE);
                score++;
                player.addVirusToPlayer(username,currentVirus);
                if(score==4){
                    stopTimer();
                    ct.cancel();
                    intent =new Intent( getActivity(), WinActivity.class);
                    intent.putExtra("USER",username);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    startActivity(intent);
                }
                scoreTxt.setText("Score: "+Integer.toString(score));
            }
        });
        four.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                v.setVisibility(View.GONE);
                score++;
                player.addVirusToPlayer(username,currentVirus);
                if(score==4){
                    stopTimer();
                    ct.cancel();
                    intent =new Intent( getActivity(), WinActivity.class);
                    intent.putExtra("USER",username);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    startActivity(intent);
                }
                scoreTxt.setText("Score: "+Integer.toString(score));
            }
        });
        ct=new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTxt.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent;
                timerTxt.setText("done!");
                if(score==4){
                    intent =new Intent( getActivity(), WinActivity.class);
                    intent.putExtra("USER",username);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    startActivity(intent);
                }else{
                    intent =new Intent( getActivity(), LoseActivity.class);
                    intent.putExtra("USER",username);
                    //need to pass information about which user is logged in
                    intent.putExtra("virusName",currentVirus);
                    startActivity(intent);
                }
                stopTimer();
                cancel();
            }
        }.start();
        startRandomButton(one,two,three,four);

        List<Pair<String, String>> PV = player.getPlayerAndVirus();
        showPlayerAndVirus(PV);
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
    private void startRandomButton(final ImageButton button1, final ImageButton button2, final ImageButton button3, final ImageButton button4) {
        timer = new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                setButtonRandomPosition(button1);
                setButtonRandomPosition(button2);
                setButtonRandomPosition(button3);
                setButtonRandomPosition(button4);
            }
        };
        timer.schedule(task,0,1000);//Update button every second
    }

    public void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer=null;
        }
    }

    private void showPlayerAndVirus(List<Pair<String, String>> list){
        for(int i=0 ;i<list.size(); i++){
            Log.d("PlayerCaughtVirus info","Player "+list.get(i).first + " Virus " + list.get(i).second);

        }

    }

}