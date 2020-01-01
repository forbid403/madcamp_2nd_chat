package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bitgaram.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

    ArrayList<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
            R.drawable.cat6, R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
            R.drawable.dog5, R.drawable.dog6));

    private GridView gallery;
    private GalleryGridAdapter gridAdapter;
    private Button signUpBtn;

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

        gallery = (GridView)rootView.findViewById(R.id.gridView);
        gridAdapter = new GalleryGridAdapter(getContext(), R.layout.imagecell, images);
        gallery.setAdapter(gridAdapter);


        return rootView;
    }



}
