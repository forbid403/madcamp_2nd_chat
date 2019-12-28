package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitgaram.R;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<AddressData> addresses;

    public AddressAdapter(ArrayList<AddressData> addresses) {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View addressView = layoutInflater.inflate(R.layout.addressrecycle, parent,false);

        ViewHolder viewHolder = new ViewHolder(addressView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressData address = addresses.get(position);
        TextView txtName = holder.name;
        txtName.setText(address.name);
        TextView txtPhone = holder.phone;
        txtPhone.setText(address.phone);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView phone;

    public ViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.addressName);
        phone = (TextView) itemView.findViewById(R.id.addressPhone);
    }
}
