package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.my.activity.MyProjectManagerActivity;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/5/16.
 */

public class MyProjectManagerAdapter extends RecyclerView.Adapter<MyProjectManagerAdapter.MyViewHolder>{

    private Context context ;
    private List<String> list ;
    public MyProjectManagerAdapter(MyProjectManagerActivity myProjectManagerActivity, List<String> datas) {
        this.context = myProjectManagerActivity ;
        this.list = datas ;
    }

    @Override
    public MyProjectManagerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_my_project_manager_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyProjectManagerAdapter.MyViewHolder holder, int position) {

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
