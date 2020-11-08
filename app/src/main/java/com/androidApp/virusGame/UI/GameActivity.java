package com.androidApp.virusGame.UI;

import androidx.fragment.app.Fragment;

public class GameActivity extends SingleFragmentActivity  {
    @Override
    protected Fragment createFragment() {
        return new  GameFragment();
    }


}
