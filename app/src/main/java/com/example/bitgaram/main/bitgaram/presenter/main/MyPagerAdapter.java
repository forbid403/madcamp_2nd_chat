package com.example.bitgaram.main.bitgaram.presenter.main;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bitgaram.main.bitgaram.presenter.main.fragment.AddressFragment;
import com.example.bitgaram.main.bitgaram.presenter.main.fragment.FindRelativeFragment;
import com.example.bitgaram.main.bitgaram.presenter.main.fragment.GalleryFragment;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] pageNames = {"연락처", "갤러리", "미정"};

    private static int NUM_PAGES = 3;
    public MyPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AddressFragment.newInstance();
            case 1:
                return GalleryFragment.newInstance();
            case 2:
                return FindRelativeFragment.newInstance();

            default: return null;

        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageNames[position];
    }
}
