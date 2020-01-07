package com.example.bitgaram.main.bitgaram.presenter.main.chatting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bitgaram.R;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private List <Message> messageList;
    private TextView chatNick;
    private TextView chatMsg;

    public MessageListAdapter(List<Message> message) {
        this.messageList = message;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msgitem, parent, false);
        chatNick = (TextView)rootView.findViewById(R.id.chatNick);
        chatMsg = (TextView)rootView.findViewById(R.id.chatMsg);

        chatNick.setText(messageList.get(position).getMessage());
        chatMsg.setText(messageList.get(position).getAuthor());

        return rootView;
    }
}
