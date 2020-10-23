package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidApp.virusGame.Model.Player;
import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{


    private EditText mEtUsername;
    private EditText mEtPassword;



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Activity activity = getActivity();

        if (activity != null){

            mEtUsername = v.findViewById(R.id.username);
            mEtPassword = v.findViewById(R.id.password);

            Button btnAdd = v.findViewById(R.id.done_button);
            btnAdd.setOnClickListener(this);
            Button btnClear = v.findViewById(R.id.clear_button);
            btnClear.setOnClickListener(this);
            Button btnChange = v.findViewById(R.id.change_password_button);
            btnClear.setOnClickListener(this);

        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.done_button:
                if(checkAccount(mEtUsername.getText().toString(),mEtPassword.getText().toString())==0){
                    intent =new Intent( getActivity(), MaskCheckActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.clear_button:
                mEtUsername.setText("");
                mEtPassword.setText("");
                break;
            case R.id.change_password_button:
                //TODO: it's not moving to this activity
                intent =new Intent( getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            case R.id.exit_button:
                //TODO: go back to another activity
                /*Only for debugging purpose
                showStoredPlayers() ;
                deleteAllPlayers() ;

                 */


        }
    }
    public int checkAccount(String username, String password){
        //TODO: clear text edits and ask for new password, etc.
        FragmentActivity activity=getActivity();
        PlayerSingleton singleton = PlayerSingleton.get(activity.getApplicationContext());
        Player player= new Player(username, password);
        int check=singleton.checkLoginCredentials(username,password);
        if(check==0){
            //login successful
            Toast.makeText(activity.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            return 0;
        }else if(check==1){
            //player not found with that username
            Toast.makeText(activity.getApplicationContext(), "Player not found with that username", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(check==2){
            //incorrect password
            Toast.makeText(activity.getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            return 2;
        }else{
            //other error
            Toast.makeText(activity.getApplicationContext(), "Other error", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}