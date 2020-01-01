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
    private static final String SERVER_ADDRESS = "http://caee68c7.ngrok.io";
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

    //Usage
    /*
    String inputQuery 는 { source : string , dest : string } 으로 전송 된다.
    이 함수는 서버와 연결하여 resultListen 을 표시하는 곳에 이용된다.
    따라서 ResultListner 에 해당하는 소켓 리스너를 반드시 구현하여야 한다.
    */
    public void QueryRelative(final String source, final String dest, Emitter.Listener resultListener) {
        if(mSocket.connected() == false) {
            Log.e("Server", "Socket is closed");
            return;
        }

        String clientQueryToJson = "{ \"source\" : \"" + source + "\", " + "\"dest\" : \"" + dest + "\"}";
        mSocket.emit(CLIENT_QUERY, clientQueryToJson);
        //결과 값을 받아서 처리할 내용
        mSocket.on(SERVER_RESULT, resultListener);
    }

    //개인 정보 수정
    public void ChangeInformation(InformationData info) {
        if(mSocket.connected() == false) {
            Log.e("Server", "Socket is closed");
            return;
        }

        mSocket.emit(CLIENT_CHANGE_INFORMATION, info.InformationToJson());
    }

    //연결 관계 수정
    public void ChangeRelative(final ArrayList<AddressData> addresses) {
        if(mSocket.connected() == false) {
            Log.e("Server", "Socket is closed");
            return;
        }

        //결과값을 서버에게 보내는 리스너
        String addressJSON = "{ \"source\" : \"" + phoneNumber + "\", \"dest\" : [" ;
        for (int i = 0; i < addresses.size(); i++) {
            addressJSON = addressJSON + " \"" + addresses.get(i).phone + "\"";
            if(i != (addresses.size()-1)) {
                addressJSON = addressJSON + ",";
            }
        }
        addressJSON = addressJSON + "]}";

        mSocket.emit(CLIENT_CHANGE_RELATIVE, addressJSON);
    }

    public static ArrayList<InformationData> ResultToInformationArrayList(JSONArray resultJSON) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InformationData>>(){}.getType();
        ArrayList<InformationData> result = gson.fromJson(resultJSON.toString(), type);

        return result;
    }
}
