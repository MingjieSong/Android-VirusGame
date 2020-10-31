package com.androidApp.virusGame.UI;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.os.Bundle;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){return new HomeFragment();}
}