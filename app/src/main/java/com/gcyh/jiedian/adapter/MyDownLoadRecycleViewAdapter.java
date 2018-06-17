package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.http.UrlAll;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/17.
 */

public class MyDownLoadRecycleViewAdapter extends RecyclerView.Adapter<MyDownLoadRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<Map<String, String>> list;
    private int process ;
    private String id ;
    private int position ;

    public MyDownLoadRecycleViewAdapter(FragmentActivity activity, List<Map<String, String>> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    public void setProcess(int process, String id) {
        this.process = process;
        this.id = id ;
        if (list.get(position).get("id").equals(id +"")){
            notifyItemChanged(position);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_downloading_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        this.position = position ;
        Map<String, String> map = list.get(position);
        if (map.get("id").equals(id)){
            holder.rbMyDownloadingItemProgress.setProgress(process);
        }
        String url = map.get("imageUrl");
        String title = map.get("title");
        String mb = map.get("mb");
        Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + url + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(holder.ivMyDownloadingItemPic);
        holder.tvMyDownloadingItemName.setText(title);
        holder.tvMyDownloadingItemNumber.setText(mb+"M");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_downloading_item_pic)
        ImageView ivMyDownloadingItemPic;
        @BindView(R.id.tv_my_downloading_item_name)
        TextView tvMyDownloadingItemName;
        @BindView(R.id.tv_my_downloading_item_type)
        TextView tvMyDownloadingItemType;
        @BindView(R.id.tv_my_downloading_item_number)
        TextView tvMyDownloadingItemNumber;
        @BindView(R.id.ll_my_downloading_item)
        LinearLayout llMyDownloadingItem;
        @BindView(R.id.rb_my_downloading_item_progress)
        ProgressBar rbMyDownloadingItemProgress;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
