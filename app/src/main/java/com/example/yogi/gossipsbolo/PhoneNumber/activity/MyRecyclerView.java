package com.example.yogi.gossipsbolo.PhoneNumber.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogi.gossipsbolo.R;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by Yogi on 11-01-2017.
 */

public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder>{

       private LayoutInflater inflater;
       Context context;

       List<Information> data= new ArrayList<>();

    MyRecyclerView(Context context)
      {
        inflater = LayoutInflater.from(context);
        this.context =context;
      }

    @Override
    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
         View view= inflater.inflate(R.layout.customrow_contactfetching,null,false);
         MyRecyclerView.ViewHolder holder = new MyRecyclerView.ViewHolder(view);
         return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerView.ViewHolder holder, int position)
    {
        Information current =data.get(position);
        holder.contactName.setText(current.ContactName);
        holder.contactNumber.setText(current.PhoneNumber);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public void addItemList(List<Information> list)
    {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView contactName,contactNumber;

        public ViewHolder(View itemView)
        {
            super(itemView);
            contactName= (TextView) itemView.findViewById(R.id.custom_contactName);
            contactNumber = (TextView) itemView.findViewById(R.id.custom_contactNumber);
        }

    }
}
