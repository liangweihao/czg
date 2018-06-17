package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcyh.jiedian.R;

import java.util.List;

/**
 * Created by Cauzhiguang on 2018/4/19.
 */

public class FindItemTypeRecyclerAdapter extends RecyclerView.Adapter<FindItemTypeRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    public FindItemTypeRecyclerAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.list = mDatas;
    }

    @Override
    public FindItemTypeRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_find_item_type_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FindItemTypeRecyclerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
