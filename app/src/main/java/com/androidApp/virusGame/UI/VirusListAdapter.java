package com.androidApp.virusGame.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

public class VirusListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "VirusListAdapter";
    Context mContext;
    String[] names;
    int [] virus;


    public VirusListAdapter(Context context, String[] virusNames, int [] virusImage){
        super(context,R.layout.adapter_view_layout);
        this.mContext = context;
        this.names = virusNames;
        this.virus = virusImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_view_layout, parent, false);
             mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.image);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mImage.setImageResource(virus[position]);
        mViewHolder.mName.setText(names[position]);

        return convertView;
    }

    public int getCount(){
        return names.length;
    }

    static class ViewHolder {
        ImageView mImage;
        TextView mName;
    }

}
