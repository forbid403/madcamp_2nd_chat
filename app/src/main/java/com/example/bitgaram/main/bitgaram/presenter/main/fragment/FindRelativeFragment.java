package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.app.Activity;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FindRelativeFragment extends Fragment {
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

        connectToSever.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final StringBuilder outputString = new StringBuilder();

                NetworkManager.QueryRelative("01031241057", inputPhoneNumber.getText().toString(), new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONArray result = (JSONArray) args[0];
                        ArrayList<Information> resultList = NetworkManager.ServerResultToInformation(result);

                        for(Information info : resultList) {
                            outputString.append("Name : " + info.name + "\n" + "Message : " + info.message + "\n");
                        }

                        getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.d("debug Server Result", outputString.toString());
                                                            outputResult.setText(outputString.toString());
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
