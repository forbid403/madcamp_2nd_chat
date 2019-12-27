package com.example.bitgaram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTabHost();


    }

    private void setTabHost() {
        TabHost tabHost1 = (TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec addressTab = tabHost1.newTabSpec("address ts");
        addressTab.setContent(R.id.address);
        addressTab.setIndicator("주소록");
        tabHost1.addTab(addressTab);

        TabHost.TabSpec galleryTab = tabHost1.newTabSpec("gallery ts");
        galleryTab.setContent(R.id.gallery);
        galleryTab.setIndicator("갤러리");
        tabHost1.addTab(galleryTab);

        TabHost.TabSpec contentTab = tabHost1.newTabSpec("content ts");
        contentTab.setContent(R.id.content3);
        contentTab.setIndicator("미정");
        tabHost1.addTab(contentTab);



    }
}
