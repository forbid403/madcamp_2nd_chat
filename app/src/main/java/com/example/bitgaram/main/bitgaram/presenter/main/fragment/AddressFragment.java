package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.bitgaram.main.bitgaram.presenter.main.fragment.SignUpActivity.mynumber;

public class AddressFragment extends Fragment {
    NetworkManager networkManager = NetworkManager.newInstance(EnvironmentData.phoneNumber);
    private ArrayList<AddressData> addresses;
    private AddressAdapter addressAdapter;
    private RecyclerView recyclerView;

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

        //Recycler view 기본설정
        addressAdapter = new AddressAdapter(addresses);
        recyclerView = rootView.findViewById(R.id.addressView);

        //Address Button 리스너 추가
        FloatingActionButton addressSync = rootView.findViewById(R.id.addressSync);
        addressSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //기존 주소록을 지우고 다시 동기화
                addresses.clear();
                String number = mynumber;
                //주소록 서버에서 받기
                new JSONTask().execute("http://2dbfafd4.ngrok.io/user/find/"+ number);
                Toast.makeText(getContext(), "메세지 동기화 중 입니다", Toast.LENGTH_LONG).show();

            }
        });

        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    Log.d("맫", buffer.toString());
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null){
                Toast.makeText(getContext(), "No Input", Toast.LENGTH_SHORT).show();
            }
            else {
                //받아온 데이터 JSON으로 파싱
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONArray addressObject = jsonArray.getJSONObject(0).getJSONArray("address");

                    for(int i=0; i<addressObject.length(); i++){
                        AddressData data = new AddressData(addressObject.getJSONObject(i).getString("name"), addressObject.getJSONObject(i).getString("phonenum"));
                        addresses.add(data);
                    }

                    recyclerView.setAdapter(addressAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    addressAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}
