package com.example.yogi.gossipsbolo.PhoneNumber.activity;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yogi on 12-01-2017.
 */

public class DataBaseAdapter {

    Context context;
    private static final String MOBILENUMBER = "97 87 123456";
    private static final String MOBILENUMBER1 = "97871 23456";
    private String array_split[];

    public DataBaseAdapter(Context context) {
        this.context = context;
    }

    public List<Information> getData()
    {
        Cursor people = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        List<Information> list = new ArrayList<>();
        Log.d("phonenumber", "cursor resolved");

        while (people.moveToNext())
        {
            Information information = new Information();

            String contactName = people.getString(people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            information.ContactName = contactName;
            Log.d("phonenumber", " " + information.ContactName);

            String contactNumber = people.getString(people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if (contactNumber.startsWith("+"))
            {
                contactNumber = contactNumber.substring(3);
                Log.d("Phonenumber", "contactno-" + contactNumber);

                array_split = contactNumber.split(" ");

                StringBuffer stringBuffer = new StringBuffer();
                for (String arr : array_split)
                {
                    contactNumber = stringBuffer.append(arr).toString();
                }
            }

            else
            {
                array_split = contactNumber.split(" ");
                Log.d("phoneNumber", "splited");

                StringBuffer stringBuffer1 = new StringBuffer();

                for (String arr : array_split)
                {
                    contactNumber = stringBuffer1.append(arr).toString();
                    Log.d("phoneNumber", "stringBuffert to string success");
                }
            }
            information.PhoneNumber = contactNumber;

            list.add(information);

            }
            people.close();

            Collections.sort(list, new Comparator<Information>()
            {
                @Override
                public int compare(Information information, Information t1)
                {
                    return information.ContactName.compareTo(t1.ContactName);
                }
            });

            return list;
    }

}



