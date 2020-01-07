package com.example.bitgaram.main.bitgaram.presenter.main.chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.fragment.FindRelativeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bitgaram.main.bitgaram.presenter.main.fragment.NetworkManager.SERVER_ADDRESS;

public class ChattingActivity extends Fragment {

    private ListView chatListView;
    private List<ChatRoom> roomList;
    private RoomListAdapter roomAdapter;

    private final String roomId = "1";
    private String creatorId = "123";

    public static ChattingActivity newInstance(){
        ChattingActivity chat = new ChattingActivity();
        return chat;
    }

    private String getAuthorId(){
        SharedPreferences pref = getContext().getSharedPreferences("pref",MODE_PRIVATE);
        return pref.getString("phone", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chattingactivity, container, false);
        chatListView = rootView.findViewById(R.id.chatlist);

        new JSONTask().execute(SERVER_ADDRESS + "chat/" + "123");
        chatListView.setOnItemClickListener(itemClickListener);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomList = new ArrayList<>();
        creatorId = getAuthorId();

    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(view.getContext(), InsideChattingRoom.class);
            intent.putExtra("roomId", String.valueOf(position));
            intent.putExtra("authorId", String.valueOf(creatorId));
            startActivity(intent);
        }
    };

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    Log.d("맫", urls[0]);
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder jsonString = new StringBuilder();
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        jsonString.append(line);
                    }

                    JSONArray data = new JSONArray(jsonString.toString());

                    for(int i=0; i<data.length(); i++){
                        JSONObject object = data.getJSONObject(i);
                        String id = object.getString("id");
                        String creator = object.getString("creator");
                        JSONArray member = object.getJSONArray("member");
                        List<String> memberList = new ArrayList<>();

                        for(int j=0; j<member.length(); j++){
                            memberList.add(member.get(j).toString());
                        }
                        ChatRoom room = new ChatRoom(id, creator, memberList);
                        roomList.add(room);
                        roomAdapter = new RoomListAdapter(roomList);
                        roomAdapter.notifyDataSetChanged();

                    }

                    return jsonString.toString();

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            chatListView.setAdapter(roomAdapter);
        }
    }
}
