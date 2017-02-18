package com.example.yogi.gossipsbolo.PhoneNumber.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.yogi.gossipsbolo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static android.R.id.list;

/**
 * Created by Yogi on 11-01-2017.
 */

public class PhoneNumberActivity  extends AppCompatActivity {

     RecyclerView recyclerView;
     List<Information> list = new ArrayList<Information>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Context context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPhoneNumberFetching);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.d("phonenumber","layoutmanager working");

        recyclerView.setVerticalScrollBarEnabled(true);

        MyRecyclerView myRecyclerView=new MyRecyclerView(PhoneNumberActivity.this);
        recyclerView.setAdapter(myRecyclerView);
        Log.d("phonenumber","adapter view holder working");

        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(PhoneNumberActivity.this);
        list = dataBaseAdapter.getData();

        myRecyclerView.addItemList(list);
        Log.d("phonenumber","database adapter list working");
    }
}