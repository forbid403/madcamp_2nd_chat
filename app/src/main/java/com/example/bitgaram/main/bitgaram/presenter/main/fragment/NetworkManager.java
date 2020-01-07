package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NetworkManager {
    public static final String SERVER_ADDRESS = "https://af2bd4ab.ngrok.io/";
    private static final String SERVER_RESULT = "result";
    private static final String CLIENT_QUERY = "query";
    private static final String CLIENT_PHONE = "clientPhoneNumber";
    private static final String CLIENT_CHANGE_INFORMATION = "changeInfo";
    private static final String CLIENT_CHANGE_RELATIVE = "changeRelative";
    public String phoneNumber = "";
    Socket mSocket;

    private static NetworkManager singleton;

    public static NetworkManager newInstance(String phoneNumber) {
        if(singleton == null) {
            singleton = new NetworkManager(phoneNumber);
            return singleton;
        }
        else {
            singleton.phoneNumber = phoneNumber;
            return singleton;
        }

    }

    public void Connect() {
        if(mSocket == null) {
            try {
                mSocket = IO.socket(SERVER_ADDRESS);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if(mSocket.connected())
            return;

        try {
            mSocket.connect();
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mSocket.emit(CLIENT_PHONE, phoneNumber);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NetworkManager(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        Connect();
    }


    public static ArrayList<InformationData> ResultToInformationArrayList(JSONArray resultJSON) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InformationData>>(){}.getType();
        ArrayList<InformationData> result = gson.fromJson(resultJSON.toString(), type);


        return result;
    }
}
