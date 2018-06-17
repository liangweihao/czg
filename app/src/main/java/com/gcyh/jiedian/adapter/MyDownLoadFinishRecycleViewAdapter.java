package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class MyDownLoadFinishRecycleViewAdapter extends RecyclerView.Adapter<MyDownLoadFinishRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<Map<String, String>>  list;

    public MyDownLoadFinishRecycleViewAdapter(FragmentActivity activity, List<Map<String, String>> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_download_finish_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Map<String, String> map = list.get(position);
        String url = map.get("imageUrl");
        String title = map.get("title");
        String mb = map.get("mb");
        String time = map.get("time");
        Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + url + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(holder.ivMyDownloadFinishItemPic);
        holder.tvMyDownloadFinishItemName.setText(title);
        holder.tvMyDownloadFinishItemType.setText(mb+"M");
        holder.tvMyDownloadFinishItemTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_download_finish_item_pic)
        ImageView ivMyDownloadFinishItemPic;
        @BindView(R.id.tv_my_download_finish_item_name)
        TextView tvMyDownloadFinishItemName;
        @BindView(R.id.tv_my_download_finish_item_type)
        TextView tvMyDownloadFinishItemType;
        @BindView(R.id.tv_my_download_finish_item_time)
        TextView tvMyDownloadFinishItemTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
