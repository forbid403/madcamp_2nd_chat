package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.icu.text.IDNA;
import android.net.Network;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NetworkManager {
    private static final String SERVER_ADDRESS = "http://192.168.0.82:8081";
    private static final String SERVER_RESULT = "result";
    private static final String CLIENT_QUERY = "query";

    public void SyncPhoneWithServer() {

    }

    //Usage
    /*
    String inputQuery 는 { source : string , dest : string } 으로 전송 된다.
    이 함수는 서버와 연결하여 resultListen 을 표시하는 곳에 이용된다.
    따라서 ResultListner 에 해당하는 소켓 리스너를 반드시 구현하여야 한다.
    */
    public static void QueryRelative(String source, String dest, Emitter.Listener resultListner) {
        final Socket mSocket;
        final String inputQuery = ClientQueryToJson(source, dest);

        try {
            mSocket = IO.socket(SERVER_ADDRESS);
            mSocket.connect();
            //결과값을 서버에게 보내는 리스너
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    mSocket.emit(CLIENT_QUERY, inputQuery);
                }
            });
            //결과 값을 받아서 처리할 내용
            mSocket.on(SERVER_RESULT, resultListner);
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //서버에 관련한 메세지 처리시 JSON 파일과 자료형으로 바꾸어주는 메소드를 정의한다.
    public static String ClientQueryToJson(String source, String dest) {
        return "{ source : '" + source + "', " + "dest : '" + dest + "'}";
    }

    public static ArrayList<Information> ServerResultToInformation(JSONArray resultJSON) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Information>>(){}.getType();
        ArrayList<Information> result = gson.fromJson(resultJSON.toString(), type);
        //Debugging usage
        for(Information info : result) {
            Log.d("Received Data : ", info.name + "/" + info.message);
        }

        return result;
    }
}
