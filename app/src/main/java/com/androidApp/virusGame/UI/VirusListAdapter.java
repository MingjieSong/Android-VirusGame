package com.androidApp.virusGame.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidApp.virusGame.Model.Virus;
import com.androidApp.virusGame.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class VirusListAdapter extends ArrayAdapter<Virus> {

    private static final String TAG = "VirusListAdapter";


    private Context mContext;
    int mResource;

    public VirusListAdapter(Context context, int resource, ArrayList<Virus> objects){
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());
        

        tvName.setText(name);
        return convertView;
    }



}
