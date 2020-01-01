package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.EnvironmentData;

import java.util.ArrayList;

import java.io.InputStream;

public class SignUpActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText phoneNum;
    private EditText description;
    private RadioGroup infoPublicize;
    private Button authorizationBtn;
    private boolean isAuthorized = false;
    private Button signUpBtn;
    private RadioButton checked;
    private EditText nameEdit;
    private static final int REQUEST_CODE = 999;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();

        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
        profilePhoto.setBackground(new ShapeDrawable(new OvalShape()));
        profilePhoto.setClipToOutline(true);

        loadComponents();

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
        if(REQUEST_CODE == requestCode){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap image = BitmapFactory.decodeStream(in);
                    in.close();
                    profilePhoto.setImageBitmap(image);

                }catch (Exception e){

                }
            }

        }else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener authorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isAuthorized = true;
        }
    };

    private boolean checkSignUp(){
        if(nameEdit.getText().toString().equals("")){
            Toast.makeText(this, "이름을 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(phoneNum.getText().toString().equals("")){
            Toast.makeText(this, "휴대폰 번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isAuthorized){
            Toast.makeText(this, "휴대폰 번호 인증을 완료 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadComponents() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        description = (EditText)findViewById(R.id.desciption);
        authorizationBtn = (Button)findViewById(R.id.authorization);
        infoPublicize = (RadioGroup)findViewById(R.id.myinfo);
        signUpBtn = (Button)findViewById(R.id.signupBtn);
        nameEdit = (EditText)findViewById(R.id.name);

        //adapt listener
        signUpBtn.setOnClickListener(signUpListener);
        authorizationBtn.setOnClickListener(authorListener);
        profilePhoto.setOnClickListener(imageSelectListener);
    }
    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!checkSignUp()){
                return;
            }
            //send data to database
            String phone = phoneNum.getText().toString();
            String desc = description.getText().toString();

            int id = infoPublicize.getCheckedRadioButtonId();
            String name = nameEdit.getText().toString();
            checked = (RadioButton)findViewById(id);
            boolean open = false;
            Bitmap profileBitmap = ((BitmapDrawable)profilePhoto.getDrawable()).getBitmap();

            if(checked.getText().toString() == "private") {
                open = false;
            }
            else {
                open = true;
            }

            EnvironmentData.phoneNumber = phone;

            ArrayList<AddressData> addresses = AddressManager.JsonToAddress(AddressManager.LoadJson(getApplicationContext()));
            Log.d("json",AddressManager.AddressToJson(addresses));

            NetworkManager networkManager = NetworkManager.newInstance(phone);
            InformationData info = new InformationData(name,phone,desc, profileBitmap, open);

            networkManager.ChangeInformation(info);
            networkManager.ChangeRelative(addresses);

            Toast.makeText(getApplicationContext(), "Sign up complete!", Toast.LENGTH_SHORT).show();
        }
    };
}
