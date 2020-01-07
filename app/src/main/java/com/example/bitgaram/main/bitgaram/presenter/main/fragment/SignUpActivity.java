package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.icu.util.Output;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.EnvironmentData;
import com.example.bitgaram.main.bitgaram.presenter.main.MainActivity;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import java.io.InputStream;

import static com.example.bitgaram.main.bitgaram.presenter.main.fragment.NetworkManager.SERVER_ADDRESS;

public class SignUpActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText phoneNum;
    private EditText description;
    private Button signUpBtn;
    private EditText nameEdit;
    private static final int REQUEST_CODE = 999;
    static String name;
    static String number;
    ArrayList<AddressData> list = new ArrayList<>();
    static String myname;
    public static String mynumber;
    static String myphoto;
    ArrayList<String> gallery = new ArrayList<>();
    static String mygallery;
    static String mydescription;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
        profilePhoto.setBackground(new ShapeDrawable(new OvalShape()));
        profilePhoto.setClipToOutline(true);
        checkVerify();

        loadComponents();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET}, 1);

        } else {
            startApp();
        }

    }

    private void startApp() {
    }


    View.OnClickListener imageSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap image = BitmapFactory.decodeStream(in);
                    in.close();
                    profilePhoto.setImageBitmap(image);

                } catch (Exception e) {

                }
            }

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSignUp() {
        if (nameEdit.getText().toString().equals("")) {
            Toast.makeText(this, "이름을 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNum.getText().toString().equals("")) {
            Toast.makeText(this, "휴대폰 번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadComponents() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        description = (EditText) findViewById(R.id.desciption);
        signUpBtn = (Button) findViewById(R.id.signupBtn);
        nameEdit = (EditText) findViewById(R.id.name);

        //adapt listener
        signUpBtn.setOnClickListener(signUpListener);
        profilePhoto.setOnClickListener(imageSelectListener);
    }

    public static String BitmapToString(Bitmap bitmapPicture) {
        String encodedImage;
        bitmapPicture = Bitmap.createScaledBitmap(bitmapPicture, 300, 400, true);
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b,Base64.DEFAULT);
        encodedImage = encodedImage.replace(System.getProperty("line.separator"), "");

        return encodedImage;
    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(!checkSignUp()){
                return;
            }

            //send data to database
            mynumber = phoneNum.getText().toString();
            mydescription = description.getText().toString();

            myname = nameEdit.getText().toString();
            Bitmap profileBitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
            myphoto = BitmapToString(profileBitmap);
            getContacts(getApplicationContext());
            getGallery(getApplicationContext());

            new JSONTask().execute(SERVER_ADDRESS + "user/signup");

            //save Information Data
            InformationData data = new InformationData(myname, mydescription, mynumber, profileBitmap);

            //session update
            UserSession session = new UserSession(getApplicationContext(), data);
            session.createUserSession();

        }
    };
    public void getContacts(Context context) {
        ContentResolver resolver = context.getContentResolver();

        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(projection[0]);
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);

                name = cursor.getString(nameIndex);
                number = cursor.getString(numberIndex);

               AddressData address = new com.example.bitgaram.main.bitgaram.presenter.main.fragment.AddressData(name, number);

               list.add(address);
            }
        }
    }

    public void getGallery(Context context) {
        ArrayList<Bitmap> result = new ArrayList<>();
        String str;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME };
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnDisplayname = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);

        int lastIndex;
        while (cursor.moveToNext())
        {
            String absolutePathOfImage = cursor.getString(columnIndex);
            String nameOfFile = cursor.getString(columnDisplayname);
            lastIndex = absolutePathOfImage.lastIndexOf(nameOfFile);
            lastIndex = lastIndex >= 0 ? lastIndex : nameOfFile.length() - 1;

            if (!TextUtils.isEmpty(absolutePathOfImage))
            {
                result.add(BitmapFactory.decodeFile(absolutePathOfImage));
            }
        }
        for(int i=0; i<result.size(); i++){
            str = BitmapToString(result.get(i));
            gallery.add(str);
        }

    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                try{

                    JSONArray jArray = new JSONArray();
                    JSONArray jsArray = new JSONArray();

                    for(int i = 0; i<list.size( ); i++){
                        JSONObject sObject = new JSONObject();
                        sObject.put("name", list.get(i).name);
                        sObject.put("phonenum", list.get(i).phonenum);
                        jArray.put(sObject);
                    }
                    for(int j = 0; j<gallery.size(); j++){
                        JSONObject oObject = new JSONObject();
                        oObject.put("gallery", gallery.get(j));
                        jsArray.put(oObject);
                    }

                    jsonObject.put("myname", myname);
                    jsonObject.put("phone", mynumber);
                    jsonObject.put("photo", myphoto);
                    jsonObject.put("desc", mydescription);
                    jsonObject.put("address", jArray);
                    Log.d("networking", String.valueOf(jArray));
                    jsonObject.put("gallery", jsArray);
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                HttpURLConnection con = null;
                BufferedReader reader = null;

                URL url = new URL(urls[0]);

                //연결을 함
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");//POST방식으로 보냄
                con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                con.connect();

                //서버로 보내기위해서 스트림 만듬
                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(jsonObject.toString());

                //ArrayList<AddressData> addresses = AddressManager.JsonToAddress(AddressManager.LoadJson(getApplicationContext()));
                //writer.write(AddressManager.LoadJson(getApplicationContext()));

                //InformationData info = new InformationData(name,phone,desc, profileBitmap);
                //writer.write(info.InformationToJson());

                writer.flush();
                writer.close();//버퍼를 받아줌

                //서버로 부터 데이터를 받음
                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if(result.equals("{\"result\":1}")){
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//
//                startActivity(intent);
                finish();
            }
        }
    }
}

