package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.IDNA;
import android.media.Image;
import android.util.Base64;
import android.util.Log;

import com.example.bitgaram.R;

import java.io.ByteArrayOutputStream;

public class InformationData {
    public String name;
    public String message;
    public String phone;
    public String profilePicture;
    public boolean open;


    public InformationData(String name, String phone, String message, Bitmap profilePicture, boolean open) {
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.open = open;
        this.profilePicture = BitmapToString(profilePicture);
    }

    public String InformationToJson() {
        return "{ \"name\": \"" + name + "\", " + "\"phone\": \"" + phone + "\", " + "\"message\": \"" + message + "\", " + "\"open\": \"" + open + "\", " + "\"profilePicture\": \"" + profilePicture + "\" }";
    }

    public static String BitmapToString(Bitmap bitmapPicture) {
        String encodedImage;
        bitmapPicture = Bitmap.createScaledBitmap(bitmapPicture, 300, 400, true);
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b,Base64.DEFAULT);
        encodedImage = encodedImage.replace(System.getProperty("line.separator"), "");

        return encodedImage;
    }

    public static Bitmap StringToBitmap(String bitmapString) {
        byte[] decodedString = Base64.decode(bitmapString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}
