package com.example.bitgaram.main.bitgaram.presenter.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.fragment.UserSession;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    EnvironmentData environment;
    FragmentPagerAdapter adapter;
    SharedPreferences sf;
    SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();
        UserSession session = new UserSession(getApplicationContext());
        session.checkSignUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTabViewPager();
    }

    void setTabViewPager(){
        environment = EnvironmentData.newInstance(this.getApplicationContext());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.viewPagerTab);
        tabLayout.setupWithViewPager(viewPager);
    }
}
