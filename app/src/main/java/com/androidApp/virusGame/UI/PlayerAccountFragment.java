package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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



public class PlayerAccountFragment extends Fragment implements View.OnClickListener  {


    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirm;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account, container, false);

        Activity activity = getActivity();

        if (activity != null){

            mEtUsername = v.findViewById(R.id.username);
            mEtPassword = v.findViewById(R.id.password);
            mEtConfirm = v.findViewById(R.id.password_confirm);
            Button btnAdd = v.findViewById(R.id.done_button);
            btnAdd.setOnClickListener(this);
            Button btnCancel = v.findViewById(R.id.cancel_button);
            btnCancel.setOnClickListener(this);
            Button btnExit = v.findViewById(R.id.exit_button);
            btnExit.setOnClickListener(this);

        }

        return v;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done_button:
                createAccount();
                break;
            case R.id.cancel_button:
                mEtUsername.setText("");
                mEtPassword.setText("");
                mEtConfirm.setText("");
                break;
            case R.id.exit_button:

                /*Activity activity = getActivity();
                if (activity != null) {
                    activity.finish() ;
                } */

                VirusSingleton v_singleton = VirusSingleton.get();
                v_singleton.getSingleVirus("HIV");
                v_singleton.updateSingleVirus("HIV","4","5,5");
                showStoredVirus();

                PlayerSingleton p_singleton = PlayerSingleton.get();
                p_singleton.getSinglePlayer("mingjie") ;
                p_singleton.addVirusToPlayer("mingjie","HIV");
                p_singleton.addVirusToPlayer("mingjie","flu virus");
                p_singleton.addVirusToPlayer("mingjie","COVID 19");
                List<Pair<Integer,Integer>> PV = p_singleton.getPlayerAndVirus();
                showPlayerAndVirus(PV);
                /*
                VirusSingleton singleton = VirusSingleton.get();
                singleton.getSingleVirus("HIV");
                singleton.updateSingleVirus("HIV","4","5,5");
                showStoredVirus();
                deleteAllVirus();


                PlayerSingleton singleton = PlayerSingleton.get();
                singleton.getSinglePlayer("tina") ;
                singleton.addVirusToPlayer("tina","HIV");
                List<Pair<Integer,Integer>> PV = singleton.getPlayerAndVirus();
                showPlayerAndVirus(PV);

                showStoredPlayers() ;
                deleteAllPlayers() ;
                PlayerSingleton singleton = PlayerSingleton.get();
                singleton.getSinglePlayer("mingjie") ;
                singleton.updateSinglePlayerPassword("654321", "mingjie");
                singleton.deleteSinglePlayerByName("mingjie");*/




        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAccount() {
        FragmentActivity activity = getActivity();
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirm = mEtConfirm.getText().toString();
        if (activity != null) {
            if (password.equals(confirm) && !username.equals("") && !password.equals("")) {
                PlayerSingleton singleton = PlayerSingleton.get();
                Player player= new Player(username, password);
                if(singleton.addPlayer(player)){
                    Toast.makeText(activity.getApplicationContext(), "New player info inserted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(activity.getApplicationContext(), "Duplicate username", Toast.LENGTH_SHORT).show();
                }
            } else if ((username.equals("")) || (password.equals("")) || (confirm.equals(""))) {
                Toast.makeText(activity.getApplicationContext(), "Missing entry", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Only for debugging purpose
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showStoredPlayers(){
            PlayerSingleton singleton = PlayerSingleton.get();
            List<Player> players =  singleton.getPlayers();
            for(int i=0 ;i<players.size(); i++){
                Log.d("Stored players info", "player#"+ i+" "+players.get(i).getName() + " and the password is "+ players.get(i).getPassword());
            }
            if(players.size()<=0){
                Log.d("Stored players info", "no player stored!") ;
            }

    }

    private void deleteAllPlayers(){
        FragmentActivity activity = getActivity();
        if(activity != null){
            PlayerSingleton singleton = PlayerSingleton.get();
            singleton.deleteAllPlayers();

        }
    }

    private void deleteAllVirus(){
        FragmentActivity activity = getActivity();
        if(activity != null){
            VirusSingleton singleton = VirusSingleton.get();
            singleton.deleteAllVirus();

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showStoredVirus(){
        VirusSingleton singleton = VirusSingleton.get();
        List<Virus> virus = singleton.getVirus();
        for(int i=0 ;i<virus.size(); i++){
            Log.d("Stored virus info", "virus#"+ i+" "+virus.get(i).getName()
                    +" Hitpoint: "+virus.get(i).getHitpt()
                    + " Location: ("+virus.get(i).getLocation()+")");
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPlayerAndVirus(List<Pair<Integer,Integer>> list){
        for(int i=0 ;i<list.size(); i++){
            Log.d("PlayerCaughtVirus info","Player "+list.get(i).first + " Virus " + list.get(i).second);

        }

    }

}
