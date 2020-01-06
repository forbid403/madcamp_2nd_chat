package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.bitgaram.R;

import java.util.ArrayList;

public class GalleryGridAdapter extends BaseAdapter{
    LayoutInflater inflater;
    int layout;
    ArrayList<Bitmap> images;
    Context context;

    public GalleryGridAdapter(Context context, int layout, ArrayList<Bitmap> img){
        this.layout = layout;
        this.images = img;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(images == null){
            return 0;
        }
        else {
            return images.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null) convertView = inflater.inflate(layout, null);
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageCell);
        imageView.setImageBitmap(images.get(position));

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popup = layoutInflater.inflate(R.layout.imagedetail, null);
                popup.setAnimation(AnimationUtils.loadAnimation(context, R.anim.pull_in));
                ImageView poppop = popup.findViewById(R.id.imagedetail); //.setBackground(ContextCompat.getDrawable(context, images.get(position)));
                poppop.setImageBitmap(images.get(position));
                final PopupWindow popupWindow = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                popupWindow.setFocusable(true);
                v.setAlpha(0.5F);
                popup.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        return convertView;
    }


}
