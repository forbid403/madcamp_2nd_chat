package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitgaram.R;

import java.util.ArrayList;

public class AddressFragment extends Fragment {
    private ArrayList<AddressData> addresses;

    public static AddressFragment newInstance(){
        AddressFragment addressFragment = new AddressFragment();
        return addressFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addresses = new ArrayList<AddressData>();
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.addressfragment, container, false);
        com.example.bitgaram.main.bitgaram.presenter.main.fragment.AddressManager.getContacts(this.getContext(), addresses);

        AddressAdapter addressAdapter = new AddressAdapter(addresses);
        RecyclerView recyclerView = rootView.findViewById(R.id.addressView);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return recyclerView;
}
}
