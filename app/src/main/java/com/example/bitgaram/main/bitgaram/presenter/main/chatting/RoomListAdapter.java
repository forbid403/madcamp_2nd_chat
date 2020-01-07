package com.example.bitgaram.main.bitgaram.presenter.main.chatting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bitgaram.R;

import java.util.List;

public class RoomListAdapter extends BaseAdapter{
    private List<ChatRoom> roomList;
    private TextView roomId;
    private TextView roomMember;

    public RoomListAdapter(List<ChatRoom> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomitem, parent, false);
        roomId = (TextView)rootView.findViewById(R.id.roomId);
        roomMember = (TextView)rootView.findViewById(R.id.roomMember);

        roomId.setText(roomList.get(position).getId());
        roomMember.setText(roomList.get(position).getMember().toString());

        return rootView;
    }
}
