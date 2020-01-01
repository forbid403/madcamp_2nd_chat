package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitgaram.R;

public class SignUpActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText phoneNum;
    private EditText description;
    private RadioGroup infoPublicize;
    private Button authorizationBtn;
    private boolean isAuthorized = false;
    private Button signUpBtn;
    private RadioButton checked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();
        loadComponents();
    }

    View.OnClickListener authorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isAuthorized = true;
        }
    };


    private boolean checkSignUp(){
        if(phoneNum.getText().toString() == ""){
            Toast.makeText(this, "휴대폰 번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isAuthorized){
            Toast.makeText(this, "휴대폰 번호 인증을 완료 해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadComponents() {
        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        description = (EditText)findViewById(R.id.desciption);
        authorizationBtn = (Button)findViewById(R.id.authorization);
        infoPublicize = (RadioGroup)findViewById(R.id.myinfo);
        signUpBtn = (Button)findViewById(R.id.signupBtn);

        //adapt listener
        signUpBtn.setOnClickListener(signUpListener);
        authorizationBtn.setOnClickListener(authorListener);
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
            checked = (RadioButton)findViewById(id);

            Toast.makeText(getApplicationContext(), "Sign up complete!", Toast.LENGTH_SHORT).show();
        }
    };
}
