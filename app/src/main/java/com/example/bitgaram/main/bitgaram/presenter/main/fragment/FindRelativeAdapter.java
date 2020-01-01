package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.findrelative_item, null);

        ImageView relProfileImage = (ImageView)view.findViewById(R.id.relProfile);
        TextView relPhoneNum = (TextView)view.findViewById(R.id.relPhoneNum);

        relPhoneNum.setText(datas.get(position).phone);

        return view;
    }
}
