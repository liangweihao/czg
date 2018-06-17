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
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/3.
 */

public class MyInteractionZanMyFragmentAdapter extends RecyclerView.Adapter<MyInteractionZanMyFragmentAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    public MyInteractionZanMyFragmentAdapter(FragmentActivity activity, List<String> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_interaction_notice_my_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvMyInteractionNoticeMyItemReturn.setVisibility(View.GONE);
        holder.tvMyInteractionNoticeMyItemMy.setVisibility(View.GONE);
        holder.ivMyInteractionNoticeMyItemReturnZan.setVisibility(View.VISIBLE);
        holder.tvMyInteractionNoticeMyItemReturnContent.setText("赞了这个主题");
        //头像
        Glide.with(context).load("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg").asBitmap().into(holder.ivMyInteractionNoticeMyItemHead);

        Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201409/apic6524.jpg").asBitmap().into(holder.ivMyInteractionNoticeMyItemPic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_interaction_notice_my_item_head)
        CircleImageView ivMyInteractionNoticeMyItemHead;
        @BindView(R.id.tv_my_interaction_notice_my_item_name)
        TextView tvMyInteractionNoticeMyItemName;
        @BindView(R.id.tv_my_interaction_notice_my_item_time)
        TextView tvMyInteractionNoticeMyItemTime;
        @BindView(R.id.tv_my_interaction_notice_my_item_return)
        TextView tvMyInteractionNoticeMyItemReturn;
        @BindView(R.id.tv_my_interaction_notice_my_item_my)
        TextView tvMyInteractionNoticeMyItemMy;
        @BindView(R.id.tv_my_interaction_notice_my_item_return_content)
        TextView tvMyInteractionNoticeMyItemReturnContent;
        @BindView(R.id.iv_my_interaction_notice_my_item_return_zan)
        ImageView ivMyInteractionNoticeMyItemReturnZan;
        @BindView(R.id.iv_my_interaction_notice_my_item_pic)
        ImageView ivMyInteractionNoticeMyItemPic;
        @BindView(R.id.tv_my_interaction_notice_my_item_content)
        TextView tvMyInteractionNoticeMyItemContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
