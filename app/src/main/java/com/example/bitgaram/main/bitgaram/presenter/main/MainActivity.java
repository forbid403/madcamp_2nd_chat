package com.example.bitgaram.main.bitgaram.presenter.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.fragment.UserSession;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainContract;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainPresenter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    EnvironmentData environment;
    private MainContract.Presenter presenter;
    FragmentPagerAdapter adapter;
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    private String[] permissions = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };
    private final int MULTIPLE_PERMISSIONS = 101;

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

        presenter = new MainPresenter();
        presenter.attachView(this);
        setTabViewPager();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        environment.SaveEnvironment(getApplicationContext());
        presenter.detachView();
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
