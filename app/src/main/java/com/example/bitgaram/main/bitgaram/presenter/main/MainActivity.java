package com.example.bitgaram.main.bitgaram.presenter.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainContract;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainPresenter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    Environment environment = Environment.newInstance(this.getApplicationContext());
    private MainContract.Presenter presenter;
    FragmentPagerAdapter adapter;

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
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.viewPagerTab);
        tabLayout.setupWithViewPager(viewPager);
    }
}
