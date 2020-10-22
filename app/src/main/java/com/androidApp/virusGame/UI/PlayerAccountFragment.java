package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
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
                /*Only for debugging purpose
                showStoredPlayers() ;
                deleteAllPlayers() ;

                 */
                //FIXME pop up current activity and return to the homescreen


        }
    }

    private void createAccount() {
        FragmentActivity activity = getActivity();
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirm = mEtConfirm.getText().toString();
        if (activity != null) {
            if (password.equals(confirm) && !username.equals("") && !password.equals("")) {
                PlayerSingleton singleton = PlayerSingleton.get(activity.getApplicationContext());
                Player player= new Player(username, password);
                singleton.addPlayer(player);
                Toast.makeText(activity.getApplicationContext(), "New player info inserted", Toast.LENGTH_SHORT).show();
            } else if ((username.equals("")) || (password.equals("")) || (confirm.equals(""))) {
                Toast.makeText(activity.getApplicationContext(), "Missing entry", Toast.LENGTH_SHORT).show();
            }
        }
    }


/*Only for debugging purpose
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showStoredPlayers(){

        FragmentActivity activity = getActivity();
        if(activity != null){
            PlayerSingleton singleton = PlayerSingleton.get(activity.getApplicationContext());
            List<Player> players =  singleton.getPlayers();
            for(int i=0 ;i<players.size(); i++){
                Log.d("Stored players info", "player#"+ i+" "+players.get(i).getName());
            }
        }
    }

    private void deleteAllPlayers(){

        FragmentActivity activity = getActivity();
        if(activity != null){
            PlayerSingleton singleton = PlayerSingleton.get(activity.getApplicationContext());
            singleton.deleteAllPlayers();

        }
    }
    */



}
