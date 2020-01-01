package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.bitgaram.R;

import java.util.ArrayList;

class FindRelativeAdapter extends BaseAdapter {
    ArrayList<InformationData> datas;
    Context mContext = null;
    LayoutInflater mInflater = null;

    public FindRelativeAdapter(Context context, ArrayList<InformationData> resultList) {
        mContext = context;
        datas = resultList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.findrelative_item, null);
            ImageView relProfileImage = (ImageView)convertView.findViewById(R.id.relProfile);
            TextView relName = (TextView)convertView.findViewById(R.id.relName);
            TextView relMessage = (TextView)convertView.findViewById(R.id.relMessage);

            relName.setText(datas.get(position).name);
            relMessage.setText(datas.get(position).message);
            relProfileImage.setBackground(new BitmapDrawable(InformationData.StringToBitmap(datas.get(position).profilePicture)));
            this.notifyDataSetChanged();
        }

        return convertView;
    }
}
