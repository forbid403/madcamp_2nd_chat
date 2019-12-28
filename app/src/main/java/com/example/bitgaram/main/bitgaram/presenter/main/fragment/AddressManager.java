package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

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

}
