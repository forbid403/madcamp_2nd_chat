package com.example.bitgaram.main.bitgaram.presenter.main.presenter;

import androidx.viewpager.widget.ViewPager;

public class MainPresenter implements MainContract.Presenter, ViewPager.OnPageChangeListener {
    private MainContract.View view;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
