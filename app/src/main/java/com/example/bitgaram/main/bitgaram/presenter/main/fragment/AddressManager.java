package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class AddressManager {
    public static void getContacts(Context context, ArrayList<AddressData> addresses) {
        ContentResolver resolver = context.getContentResolver();

        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER };

        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        if(cursor != null) {
            while(cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(projection[0]);
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);

                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);

                AddressData address = new com.example.bitgaram.main.bitgaram.presenter.main.fragment.AddressData(name, number);

                addresses.add(address);
            }
        }
    }

    public static String AddressToJson(ArrayList<AddressData> input) {
        Gson gson = new Gson();
        String output = gson.toJson(input, new TypeToken<ArrayList<AddressData>>(){}.getType());

        return output;
    }

    public static ArrayList<AddressData> JsonToAddress(String input) {
        ArrayList<AddressData> outputList = new ArrayList<AddressData>();
        Gson gson = new Gson();
        outputList = gson.fromJson(input, new TypeToken<ArrayList<AddressData>>(){}.getType());

        if(outputList == null) {
            outputList = new ArrayList<AddressData>();
            outputList.add(new AddressData("연락처 동기화 해주세요"," "));
            return outputList;
        }

        return outputList;
    }

    public static void SaveJson(String input, Context context) {
        BufferedWriter bfwr = null;
        String dirPath = context.getFilesDir().getAbsolutePath();

        try {
            FileOutputStream fos = new FileOutputStream(dirPath + "/" + "addressJSON.txt",false);
            bfwr = new BufferedWriter(new OutputStreamWriter(fos));
            bfwr.write(input);
            bfwr.flush();

            bfwr.close();
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("file","파일 저장 실패");
        }
    }

    public static String LoadJson(Context context) {
        String dirPath = context.getFilesDir().getAbsolutePath();
        File file = new File(dirPath + "/" + "addressJSON.txt");
        FileReader fr = null;
        BufferedReader brrd = null;
        StringBuilder outputString = new StringBuilder();
        String line;

        try {
            fr = new FileReader(file);
            brrd = new BufferedReader(fr);

            while((line = brrd.readLine()) != null) {
                outputString.append(line);
            }

            brrd.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("file","파일 불러오기 실패");
            return "";
        }

        return outputString.toString();
    }
}
