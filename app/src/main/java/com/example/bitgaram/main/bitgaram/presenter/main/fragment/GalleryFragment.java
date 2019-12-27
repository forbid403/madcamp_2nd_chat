package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;

public class GalleryFragment extends Fragment {
    int images[] = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
                    R.drawable.cat6, R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
                    R.drawable.dog5, R.drawable.dog6};

    public static GalleryFragment newInstance(){
        GalleryFragment galleryFragment = new GalleryFragment();
        return galleryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.galleryframent, container, false);
        GridView gallery = (GridView)rootView.findViewById(R.id.gridView);
        gallery.setAdapter(new GalleryGridAdapter(getContext(), R.layout.imagecell, images));
        return rootView;
    }
}
