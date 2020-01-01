package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.EnvironmentData;

import org.json.JSONArray;

import java.util.ArrayList;

import io.socket.emitter.Emitter;

public class FindRelativeFragment extends Fragment {
    String phoneNumber = EnvironmentData.phoneNumber;
    NetworkManager networkManager = NetworkManager.newInstance(phoneNumber);

    public static FindRelativeFragment newInstance(){
        FindRelativeFragment frg = new FindRelativeFragment();
        return frg;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findrelativefragment, container, false);

        //각각 서버 처리에 대한 리스너 추가하여, 그에 따른 응답을 처리 하는 코드
        Button connectToSever = rootView.findViewById(R.id.connectToServerBtn);
        final EditText inputPhoneNumber = rootView.findViewById(R.id.inputPhoneNumber);
        final ListView outputResult = rootView.findViewById((R.id.resultRelative));

        connectToSever.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final StringBuilder outputString = new StringBuilder();
                final ArrayList<InformationData> resultList = new ArrayList<>();

                networkManager.QueryRelative(phoneNumber, inputPhoneNumber.getText().toString(), new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONArray result = (JSONArray) args[0];
                        final ArrayList<InformationData> resultList = NetworkManager.ResultToInformationArrayList(result);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "hh", Toast.LENGTH_SHORT).show();
                                //do listview something
                                FindRelativeAdapter findAdapter = new FindRelativeAdapter(getContext(), resultList);
                                outputResult.setAdapter(findAdapter);
                                outputResult.deferNotifyDataSetChanged();
                            }
                        });

                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
