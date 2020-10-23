package com.androidApp.virusGame.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidApp.virusGame.R;


public class HomeScreenFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DebuggingMessage";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState) ;
        Log.d(TAG, "onCreate()") ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        Log.d(TAG, "onCreateView()") ;


      // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_homescreen, container, false);
        Button loginButton=v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        Button accountButton=v.findViewById(R.id.create_account);
        accountButton.setOnClickListener(this);

        return v;

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()") ;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()") ;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()") ;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()") ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()") ;
    }


    @Override
    public void onClick(View view) {
        Intent intent ;
        switch(view.getId()){

            case R.id.login_button:
                intent =new Intent( getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.create_account:

                intent =new Intent( getActivity(), PlayerAccountActivity.class);
                startActivity(intent);
                break;
        }
        /* this needs to be called after a successful login
        intent=new Intent( getActivity(), MaskCheckActivity.class);
        startActivity(intent); */
        //just called here so we can trigger onDestroy
        getActivity().finish();

    }


}
