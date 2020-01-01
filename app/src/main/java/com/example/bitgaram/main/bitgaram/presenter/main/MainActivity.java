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
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainContract;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainPresenter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    EnvironmentData environment;
    private MainContract.Presenter presenter;
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
