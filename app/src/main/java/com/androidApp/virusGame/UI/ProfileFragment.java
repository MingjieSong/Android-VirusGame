package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidApp.virusGame.Model.Player;
import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.Model.Virus;
import com.androidApp.virusGame.Model.VirusSingleton;
import com.androidApp.virusGame.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    //store virus name and count of virus
    private static Map<String, Integer> virusMap= new HashMap<String,Integer>();;
    ListView mListView;
    ArrayList<String>virusNames = new ArrayList<>();
    ArrayList<Integer>virusImage = new ArrayList<>();
    ArrayList<String>virusCount = new ArrayList<>();
    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Activity activity = getActivity();
        String username=activity.getIntent().getStringExtra("USER");

        /*

        PlayerSingleton player = PlayerSingleton.get();
        player.getSinglePlayer(username);
        List<Pair<String, String>> pv = player.getPlayerAndVirus();

        for(int i=0 ;i<pv.size(); i++){
            Log.d("PlayerCaughtVirus info","Player "+pv.get(i).first + " Virus " + pv.get(i).second);
            setMap(pv.get(i).second);
        }*/

        virusNames.add("hivvirus");
        virusNames.add("fluvirus");
        virusNames.add("coronavirus");
        virusImage.add(R.drawable.hivvirus);
        virusImage.add(R.drawable.fluvirus);
        virusImage.add(R.drawable.coronavirus);
        virusCount.add(Integer.toString(0));
        virusCount.add(Integer.toString(0));
        virusCount.add(Integer.toString(0));

        for (String key: virusMap.keySet()){
            virusNames.add(key);
            if (key == "hivvirus"){
                virusImage.add(R.drawable.hivvirus);
            }
            if (key == "fluvirus"){
                virusImage.add(R.drawable.fluvirus);
            }
            if (key == "coronavirus"){
                virusImage.add(R.drawable.coronavirus);
            }
        }

        for (Integer value: virusMap.values()){
            virusCount.add(Integer.toString(value));
        }


        if (activity != null){

            Button btnHome= v.findViewById(R.id.go_home_button);
            btnHome.setOnClickListener(this);
            TextView welcomeTxt=(TextView)v.findViewById(R.id.welcome_user);
            mListView = (ListView)v.findViewById(R.id.listView);
            welcomeTxt.setText("Welcome "+username);


            VirusListAdapter virusAdapter = new VirusListAdapter(this.getContext(),virusNames,virusImage,virusCount);
            mListView.setAdapter(virusAdapter);

        }

        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentActivity activity=getActivity();
        switch (view.getId()) {
            case R.id.go_home_button:
                if (activity != null) {
                    activity.finish() ;
                }
                break;
        }
    }

    public static void setMap(String name){
        if (!virusMap.containsKey(name)){
            virusMap.put(name,1);
        }else{
            virusMap.put(name, virusMap.get(name)+1);
        }

    }


}