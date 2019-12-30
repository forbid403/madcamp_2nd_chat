package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;

import org.json.JSONArray;

import java.util.ArrayList;

import io.socket.emitter.Emitter;

public class FindRelativeFragment extends Fragment {
    String phoneNumber = getContext().getSharedPreferences("savedData", Context.MODE_PRIVATE).getString("phoneNumber", "01031241057");
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
        final TextView outputResult = rootView.findViewById((R.id.resultRelative));
        final ImageView outputImage = rootView.findViewById(R.id.profileImage);

        connectToSever.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final StringBuilder outputString = new StringBuilder();

                networkManager.QueryRelative(phoneNumber, inputPhoneNumber.getText().toString(), new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONArray result = (JSONArray) args[0];
                        final ArrayList<InformationData> resultList = NetworkManager.ResultToInformationArrayList(result);

                        for(InformationData info : resultList) {
                            outputString.append("Name : " + info.name + "\n" + "Message : " + info.message + "\n");
                        }

                        //this is debug purpose only
                        getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.d("debug Server Result", outputString.toString());
                                                            outputResult.setText(outputString.toString());
                                                            outputImage.setImageBitmap(InformationData.StringToBitmap(resultList.get(0).profilePicture));
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
