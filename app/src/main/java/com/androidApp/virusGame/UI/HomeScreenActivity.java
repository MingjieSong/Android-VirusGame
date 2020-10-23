package com.androidApp.virusGame.UI;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.androidApp.virusGame.Model.Virus;
import com.androidApp.virusGame.Model.VirusDbHelper;
import com.androidApp.virusGame.Model.VirusDbSchema;
import com.androidApp.virusGame.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;


public class HomeScreenActivity extends SingleFragmentActivity {
    private VirusDbHelper db;
    File pinkVirusFile = new File("/app/src/main/pinkvirus-playstore.png");
    //private String pink_virus_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
    //File.separator + "pinkvirus-playstore.png";


    @Override
    protected HomeScreenFragment createFragment(){return new HomeScreenFragment() ;}


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        db = new VirusDbHelper(this);

        //insert virus
        /*
        db.addVirus(new Virus("pinkVirus","3"));
        db.addVirus(new Virus("yellowVirus","4"));
        showStoredVirus();*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save data
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Get access to more stored data
    }

    //convert bitmap image into byte array
    // Convert that image to byte array & store that byte [] to DB.
    // While retrieving that image get byte [] convert that byte [] to bitmap by which you will get original image.
    public byte[] convertToByteArray(File virusFile) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(virusFile.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showStoredVirus(){

            List<Virus> virus = db.getVirus();
            for(int i=0 ;i<virus.size(); i++){
                Log.d("Stored virus info", "virus#"+ i+" "+virus.get(i).getName());
            }

    }

}