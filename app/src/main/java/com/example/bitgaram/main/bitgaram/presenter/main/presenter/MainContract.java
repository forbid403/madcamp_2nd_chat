package com.example.bitgaram.main.bitgaram.presenter.main.presenter;

import android.content.Context;
import android.widget.TabHost;

public interface MainContract {
    interface View{
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
    }


}
