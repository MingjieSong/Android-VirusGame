package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.androidApp.virusGame.Model.PlayerSingleton;
import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteAccountFragment extends Fragment implements View.OnClickListener{


    private EditText mEtUsername;
    private EditText mEtPassword;



    public DeleteAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Activity activity = getActivity();

        if (activity != null){

            mEtUsername = v.findViewById(R.id.username);
            mEtPassword = v.findViewById(R.id.password);

            Button btnDelete = v.findViewById(R.id.delete_account_button);
            btnDelete.setOnClickListener(this);

        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.delete_account_button:
                checkAccount(mEtUsername.getText().toString(),mEtPassword.getText().toString());
                break;
        }
    }
    public int checkAccount(String username, String password){
        FragmentActivity activity=getActivity();
        PlayerSingleton singleton = PlayerSingleton.get();
        int check=singleton.checkLoginCredentials(username,password);
        if(check==0){
            //success
            singleton.deleteSinglePlayerByName(username);
            Toast.makeText(activity.getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
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