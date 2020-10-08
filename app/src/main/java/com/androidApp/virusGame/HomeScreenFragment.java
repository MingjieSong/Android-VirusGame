package com.androidApp.virusGame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


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

    //FIXME: add switch statement based on the layout
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_button:
                Intent intent=new Intent(getActivity(),MaskCheck.class);
                startActivity(intent);
                //just called here so we can trigger onDestroy
                getActivity().finish();
                break;
            case R.id.create_account:
                CharSequence pushed ="Create Account Pushed";
                Toast.makeText(getActivity(),pushed,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()") ;
    }
}
