package com.androidApp.virusGame.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class ProfileActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){return new ProfileFragment();}
}