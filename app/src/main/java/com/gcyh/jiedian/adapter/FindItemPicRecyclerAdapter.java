package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.find.activity.ImageViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/19.
 */

public class FindItemPicRecyclerAdapter extends RecyclerView.Adapter<FindItemPicRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    public FindItemPicRecyclerAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_find_item_pic_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.i("=====", "onBindViewHolder: ===2=="+list.get(position));
        Glide.with(context).load(list.get(position)).asBitmap().into(holder.ivPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> data = new ArrayList() ;
                data.addAll(list) ;
                Intent intent = new Intent(context , ImageViewActivity.class) ;
                intent.putStringArrayListExtra("typepic",data) ;
                intent.putExtra("position" , position) ;

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        ImageView ivPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
