package com.androidApp.virusGame.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.androidApp.virusGame.Model.VirusDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Virus extends AppCompatActivity {
    VirusDbHelper virusDb;
    //private String pink_virus_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
    //File.separator + "pinkvirus-playstore.png";
    File pinkVirusFile = new File("/app/src/main/pinkvirus-playstore.png");
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        virusDb = new VirusDbHelper(this);
        virusDb.insertVirus("pinkVirus",4,convertToByteArray(pinkVirusFile));
        String check = VirusDbSchema.VirusTable.Cols.NAME, name;

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
}