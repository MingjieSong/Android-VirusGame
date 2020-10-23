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
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {


    private EditText mEtUsername;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtConfirm;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Activity activity = getActivity();

        if (activity != null) {

            mEtUsername = v.findViewById(R.id.username);
            mEtOldPassword = v.findViewById(R.id.old_password);
            mEtConfirm = v.findViewById(R.id.confirm_new_password);
            mEtNewPassword = v.findViewById(R.id.new_password);

            Button btnDone = v.findViewById(R.id.done_button);
            btnDone.setOnClickListener(this);

        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.done_button:
                checkAccount(mEtUsername.getText().toString(), mEtOldPassword.getText().toString(), mEtNewPassword.getText().toString(), mEtConfirm.getText().toString());
                break;
            case R.id.exit_button:
                //TODO: go back to another activity
                /*Only for debugging purpose
                showStoredPlayers() ;
                deleteAllPlayers() ;

                 */


        }
    }

    public void checkAccount(String username, String oldPassword, String newPassword, String confirm) {
        FragmentActivity activity = getActivity();
        PlayerSingleton singleton = PlayerSingleton.get(activity.getApplicationContext());
        int check = singleton.checkLoginCredentials(username, oldPassword);
        if (check == 0) {
            //username and password are good, move on to change password
            if (newPassword.equals(confirm)) {
                check = singleton.changePassword(username, newPassword);
                if (check == 0) {
                    Toast.makeText(activity.getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity.getApplicationContext(), "Confirmation must match new password", Toast.LENGTH_SHORT).show();
            }
        } else if (check == 1) {
            Toast.makeText(activity.getApplicationContext(), "Username not found", Toast.LENGTH_SHORT).show();
        } else if (check == 2) {
            Toast.makeText(activity.getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
        }

        }
    }