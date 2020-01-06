package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.myapplication.NetworkActivity.SERVER_URL;

public class InsideChattingRoom extends AppCompatActivity {
    private List<Message> msgList;
    private MessageListAdapter adapter;
    private Socket socket;
    private EditText message;
    private Button btn;
    private ListView msgListView;
    private HttpURLConnection con;
    private BufferedReader reader;
    private String roomId;
    private String authorId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insidechattingroom);

        roomId = getIntent().getStringExtra("roomId");
        authorId = getIntent().getStringExtra("authorId");

        msgListView = (ListView)findViewById(R.id.chatlist) ;
        message = (EditText)findViewById(R.id.message);
        btn = (Button)findViewById(R.id.btn);
        msgList = new ArrayList<>();

        String messageTask = SERVER_URL + "chat/get/" + roomId;
        new GetMessagesTask().execute(messageTask);

        try {
            socket = IO.socket(SERVER_URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("msg", message.getText().toString());
                new SaveMessageTask().execute(SERVER_URL + "chat/save", message.getText().toString());
            }
        });
        socket.on("message", onMessage);

    }


    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String author = data.getString("author");
                        String content = data.getString("content");

                        Message m = new Message(author, content);
                        adapter = new MessageListAdapter(msgList);
                        adapter.notifyDataSetChanged();
                        msgListView.setAdapter(adapter);
                        msgList.add(m);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    public class SaveMessageTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                con = null;
                reader = null;

                JSONObject jsonObject = new JSONObject();
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                jsonObject.accumulate("id", roomId);
                jsonObject.accumulate("message", urls[1]);
                jsonObject.accumulate("author", authorId);
                jsonObject.accumulate("time", ts);

                Log.d("맫", jsonObject.toString());
                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Accept", "html/text");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌


                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임


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

    }

    public class GetMessagesTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                con = null;
                reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder jsonString = new StringBuilder();
                    String line = "";

                    while((line = reader.readLine()) != null){
                        jsonString.append(line);
                    }

                    JSONArray data = new JSONArray(jsonString.toString());

                    for(int i=0; i<data.length(); i++){
                        JSONObject object = data.getJSONObject(i);
                        String message = object.getString("message");
                        String author = object.getString("author");

                        Message m = new Message(author, message);
                        adapter = new MessageListAdapter(msgList);
                        adapter.notifyDataSetChanged();
                        msgList.add(m);

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
            msgListView.setAdapter(adapter);
        }
    }
}
