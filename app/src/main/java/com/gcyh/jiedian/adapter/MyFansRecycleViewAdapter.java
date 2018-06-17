package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.FanList;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.my.activity.MyFansActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;
import com.gcyh.jiedian.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/3.
 */

public class MyFansRecycleViewAdapter extends RecyclerView.Adapter<MyFansRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<FanList.ResponseParamsBean> list;

    public MyFansRecycleViewAdapter(MyFansActivity myFansActivity, List<FanList.ResponseParamsBean> mDatas) {
        this.context = myFansActivity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_my_notice_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FanList.ResponseParamsBean dataBean = list.get(position);
        final FanList.ResponseParamsBean.UserInfoBean userInfo = dataBean.getUserInfo();
        //头像
        if (!TextUtils.isEmpty(userInfo.getPhoto())){
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+dataBean.getUserInfo().getPhoto()+".png").placeholder(R.mipmap.my_head).error(R.mipmap.my_head).into(holder.ivMyNoticeItemHead);
        }
        //昵称
        holder.tvMyNoticeItemName.setText(userInfo.getNameNick());
        //A  B  S
        if (dataBean.getAVIP() == 1){
            holder.ivMyNoticeItemA.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemA.setVisibility(View.VISIBLE);
        }
        if (dataBean.getBVIP() == 1){
            holder.ivMyNoticeItemB.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemB.setVisibility(View.VISIBLE);
        }
        if (dataBean.getSVIP() == 1){
            holder.ivMyNoticeItemS.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemS.setVisibility(View.VISIBLE);
        }
        //等级
        setGrade(dataBean.getGrade() , holder.ivMyNoticeItemRank);
       // 星星
        holder.ratingBar.setStar(dataBean.getStar());
        //关注 ：0  互相关注 ：1
        if (dataBean.getMutual() == 1){
            holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.each_notice);
        }else {
            holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.notice);
        }
        //关注按钮
        holder.ivMyNoticeItemNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getMutual() == 1){
                    // 取消关注---（互相关注》关注）
                    holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.notice);
                    Bundle bundle = new Bundle() ;
                    bundle.putString("code" , userInfo.getUserCode()+"");
                    EventBusUtil.postEvent(EventBusCode.DELETE_NOTICE , bundle);
                    dataBean.setMutual(0);
                }else {
                    //关注
                    holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.each_notice);
                    Bundle bundle = new Bundle() ;
                    bundle.putString("code" , userInfo.getUserCode()+"");
                    EventBusUtil.postEvent(EventBusCode.NOTICE , bundle);
                    dataBean.setMutual(1);
                }
            }
        });

        holder.ivMyNoticeItemHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , OtherFindActivity.class) ;
                intent.putExtra("userCode" , userInfo.getUserCode()) ;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_notice_item_head)
        CircleImageView ivMyNoticeItemHead;
        @BindView(R.id.tv_my_notice_item_name)
        TextView tvMyNoticeItemName;
        @BindView(R.id.iv_my_notice_item_A)
        ImageView ivMyNoticeItemA;
        @BindView(R.id.iv_my_notice_item_B)
        ImageView ivMyNoticeItemB;
        @BindView(R.id.iv_my_notice_item_S)
        ImageView ivMyNoticeItemS;
        @BindView(R.id.iv_my_notice_item_rank)
        ImageView ivMyNoticeItemRank;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.iv_my_notice_item_notice)
        ImageView ivMyNoticeItemNotice;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setGrade(int grade, ImageView ivMyGrade) {
        switch (grade) {
            case 1:
                ivMyGrade.setImageResource(R.mipmap.lv1);
                break;
            case 2:
                ivMyGrade.setImageResource(R.mipmap.lv2);
                break;
            case 3:
                ivMyGrade.setImageResource(R.mipmap.lv3);
                break;
            case 4:
                ivMyGrade.setImageResource(R.mipmap.lv4);
                break;
            case 5:
                ivMyGrade.setImageResource(R.mipmap.lv5);
                break;
            case 6:
                ivMyGrade.setImageResource(R.mipmap.lv6);
                break;
            case 7:
                ivMyGrade.setImageResource(R.mipmap.lv7);
                break;
            case 8:
                ivMyGrade.setImageResource(R.mipmap.lv8);
                break;
            case 9:
                ivMyGrade.setImageResource(R.mipmap.lv9);
                break;
            case 10:
                ivMyGrade.setImageResource(R.mipmap.lv10);
                break;
        }
    }

}
