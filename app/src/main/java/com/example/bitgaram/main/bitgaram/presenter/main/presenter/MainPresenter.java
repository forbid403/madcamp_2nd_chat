package com.example.bitgaram.main.bitgaram.presenter.main.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.bitgaram.R;
import com.example.bitgaram.main.bitgaram.presenter.main.presenter.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }



}
