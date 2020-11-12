package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    ListView mListView;
    //store virus name and count of virus
    public Map<String, Integer> virusMap = new HashMap<String,Integer>();
    private ArrayList<Virus> virusList = new ArrayList<>();
    String [] virusNames = {"hivvirus","coronavirus"};
    int [] virusImage = {R.drawable.hivvirus,R.drawable.coronavirus};
    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Activity activity = getActivity();
        String username=activity.getIntent().getStringExtra("USER");

        if (activity != null){

            Button btnHome= v.findViewById(R.id.go_home_button);
            btnHome.setOnClickListener(this);
            TextView welcomeTxt=(TextView)v.findViewById(R.id.welcome_user);
            mListView = (ListView)v.findViewById(R.id.listView);
            welcomeTxt.setText("Welcome "+username);

            VirusSingleton singleton = VirusSingleton.get(this.getContext());
            virusList = singleton.getVirus();
            VirusListAdapter virusAdapter = new VirusListAdapter(this.getContext(),virusNames,virusImage);
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


}