package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidApp.virusGame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaskCheckFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "DebuggingMessage";

    public MaskCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_mask_check, container, false);
        Activity activity = getActivity();

        if (activity != null){
            Button btnYes = v.findViewById(R.id.yes_button);
            btnYes.setOnClickListener(this);
            Button btnNo = v.findViewById(R.id.no_button);
            btnNo.setOnClickListener(this);


        }

        return v;
    }

    @Override
    public void onClick(View view) {
        String username=getActivity().getIntent().getStringExtra("USER");
        FragmentActivity activity=getActivity();
        Intent intent;
        switch (view.getId()) {
            case R.id.yes_button:
                intent =new Intent( getActivity(), MapActivity.class);
                intent.putExtra("USER",username);
                startActivity(intent);
                break;
            case R.id.no_button:
                if (activity != null) {
                    activity.finish() ;
                }
                break;
            default:
                Log.d(TAG, "ERROR: no such case!") ;

        }
    }


}