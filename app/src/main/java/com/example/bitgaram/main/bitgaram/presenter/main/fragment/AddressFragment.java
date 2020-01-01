package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.EnvironmentData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddressFragment extends Fragment {
    NetworkManager networkManager = NetworkManager.newInstance(EnvironmentData.phoneNumber);
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
        //기본 변수 설정
        addresses = new ArrayList<AddressData>();
        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.addressfragment, container, false);
        //JSON 파일에서 주소록 가져옴
        addresses = AddressManager.JsonToAddress(AddressManager.LoadJson(getContext()));

        //Recycler view 기본설정
        final AddressAdapter addressAdapter = new AddressAdapter(addresses);
        RecyclerView recyclerView = rootView.findViewById(R.id.addressView);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Log.d("json",AddressManager.AddressToJson(addresses));

        //Address Button 리스너 추가
        FloatingActionButton addressSync = rootView.findViewById(R.id.addressSync);
        addressSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //기존 주소록을 지우고 다시 동기화
                addresses.clear();
                AddressManager.getContacts(getContext(), addresses);
                Toast syncMessage = Toast.makeText(getContext(), "메세지 동기화 중 입니다", Toast.LENGTH_LONG);
                syncMessage.show();
                addressAdapter.notifyDataSetChanged();

                //주소록을 JSON 파일로 동기화
                AddressManager.SaveJson(AddressManager.AddressToJson(addresses),getContext());

                //주소록 정보 수정을 서버에 올림
                networkManager.ChangeRelative(addresses);
            }
        });
        return rootView;
    }
}
